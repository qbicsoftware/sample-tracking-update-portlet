package life.qbic.portal.portlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import life.qbic.datamodel.people.Address;
import life.qbic.datamodel.samples.Location;
import life.qbic.datamodel.samples.Sample;
import life.qbic.datamodel.samples.Status;
import life.qbic.datamodel.services.ServiceUser;
import life.qbic.portal.utils.ConfigurationManager;
import life.qbic.portal.utils.ConfigurationManagerFactory;
import life.qbic.portal.utils.PortalUtils;
import life.qbic.services.ConsulServiceFactory;
import life.qbic.services.Service;
import life.qbic.services.ServiceConnector;
import life.qbic.services.ServiceType;
import life.qbic.services.connectors.ConsulConnector;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Entry point for portlet sample-tracking-update-portlet. This class derives from {@link
 * QBiCPortletUI}, which is found in the {@code portal-utils-lib} library.
 *
 * @see <a href=https://github.com/qbicsoftware/portal-utils-lib>portal-utils-lib</a>
 */
@Theme("mytheme")
@SuppressWarnings("serial")
@Widgetset("life.qbic.portal.portlet.AppWidgetSet")
public class SampleTrackingUpdatePortlet extends QBiCPortletUI {

  private static final Logger LOG = LogManager.getLogger(SampleTrackingUpdatePortlet.class);
  private HashMap<String, Location> locationMap;
  private String selectedLocation;
  private String selectedStatus;
  private Vector<String> validIdList;

  private static List<Service> serviceList; //TODO: why is this static?
  private static ServiceUser httpUser; //TODO: why is this static?
  private String authHeader;

  @Override
  protected Layout getPortletContent(final VaadinRequest request) {
    LOG.info("Generating content for {}", SampleTrackingUpdatePortlet.class);
    //TODO: separate service connection from UI composition
    ConfigurationManager confManager = ConfigurationManagerFactory.getInstance();
    URL serviceURL = null;
    httpUser = confManager.getServiceUser();
    try {
      serviceURL = new URL(confManager.getServicesRegistryUrl());

      serviceList = new ArrayList<Service>();
      ServiceConnector connector = new ConsulConnector(serviceURL);
      ConsulServiceFactory factory = new ConsulServiceFactory(connector);
      serviceList.addAll(factory.getServicesOfType(ServiceType.SAMPLE_TRACKING));

    } catch (MalformedURLException e) {
      e.printStackTrace(); //TODO: why not exit when there is no service? At least inform?
    }

    byte[] credentials = Base64
        .encodeBase64((httpUser.name + ":" + httpUser.password).getBytes(StandardCharsets.UTF_8));
    //TODO: consider replacing the field by an extra object handling authentication
    authHeader = "Basic " + new String(credentials, StandardCharsets.UTF_8);

    this.locationMap = new HashMap<String, Location>();
    this.selectedStatus = Status.WAITING.toString(); //TODO: why string?
    this.validIdList = new Vector<String>();
    this.selectedLocation = ""; //TODO: why string?

    //TODO: the following should be the only content of this method
    final HorizontalLayout mainLayout = new HorizontalLayout();
    mainLayout.setSpacing(true);
    mainLayout.setMargin(true);
    mainLayout.setSizeFull();

    //TODO: create object before passing it to addComponent
    mainLayout.addComponent(this.getRegistrationInterface());

    return mainLayout;
  }

  private Panel getRegistrationInterface() {

    Panel queryPanel = new Panel("qTracker: update sample status");
    queryPanel.setWidth("100%");

    HorizontalLayout mainLayout = new HorizontalLayout();
    mainLayout.setSpacing(true);
    mainLayout.setMargin(true);
    mainLayout.setWidth("100%");

    VerticalLayout panelContentA = new VerticalLayout();
    panelContentA.setSpacing(true);
    panelContentA.setMargin(true);

    VerticalLayout panelContentB = new VerticalLayout();
    panelContentB.setSpacing(true);
    panelContentB.setMargin(true);

    TextField idField = new TextField("Enter QBiC IDs:");
    idField.setValue("QABCD004AO"); //TODO: should a valid ID be the default?
    idField.setWidth("30%");

    Button addIdButton = new Button("Add ID");
    addIdButton.setWidth("30%");

    Grid logTable = new Grid("Samples to update:");
    logTable.setSelectionMode(Grid.SelectionMode.NONE);
    logTable.addColumn("ID", String.class);
    logTable.addColumn("Location", String.class);
    logTable.addColumn("Status", String.class);

    logTable.setWidth("100%");
    logTable.setHeight("30%");


    // Implement both receiver that saves upload in a file and
    // listener for successful upload
    class IdReceiver implements Upload.Receiver, Upload.SucceededListener {
    //TODO: should this class be moved out of the method?

      protected File tempFile;

      public OutputStream receiveUpload(String filename, String mimeType) {
        //TODO: String mimeType is not used?
        try {
          tempFile = File.createTempFile("temp", ".csv");
          return new FileOutputStream(tempFile);
        } catch (IOException e) {
          e.printStackTrace();
          return null;
        }
      }

      public void uploadSucceeded(Upload.SucceededEvent event) {

        try {
          BufferedReader reader = new BufferedReader(new FileReader(tempFile));
          String next;
          while ((next = reader.readLine()) != null) {

            next = next.trim();
            //TODO: remove unnecessary output
            System.out.println("-------> id: " + next);

            addIdToTable(next, logTable);
          }
          reader.close();
          tempFile.delete();

        } catch (IOException e) {
          e.printStackTrace();
          //TODO: inform about error
        }
      }
    }//close IdReceiver

    IdReceiver receiver = new IdReceiver();
    // Create the upload with a caption and set receiver later //TODO: what is meant by later?
    Upload idFileUpload = new Upload("Upload ID file", receiver);
    idFileUpload.setButtonCaption("Add File IDs");
    //TODO: the receiver is also a listener. Is this how it should be?
    idFileUpload.addSucceededListener(receiver);


    //TODO: here UI composition takes place. move to correct location
    Button clearTableButton = new Button("Clear samples");

    //TODO: why use html here and not also somewhere else?
    Label rpText = new Label("<b>Responsible Person email:</b>", ContentMode.HTML);
    //TODO: is a valid permissive mail going to be the fallback?
    Label rpEmail = new Label("sven.fillinger@qbic.uni-tuebingen.de");

    if (PortalUtils.isLiferayPortlet()) {
      rpEmail.setValue(PortalUtils.getUser().getEmailAddress());
    }

    rpText.setWidth("80%");
    rpEmail.setWidth("80%");

    Button locationsButton = new Button("Get Locations");
    locationsButton.setWidth("40%");

    ComboBox locationBox = new ComboBox("Location:");
    locationBox.setNullSelectionAllowed(false);
    locationBox.setTextInputAllowed(false);
    locationBox.setWidth("40%");
    locationBox.setEnabled(false);

    DateField dateInput = new DateField("Date:");
    dateInput.setWidth("40%");
    dateInput.setValue(new Date());

    ComboBox statusBox = new ComboBox("Sample Status:");
    statusBox.setNullSelectionAllowed(false);
    statusBox.addItem(Status.WAITING);
    statusBox.addItem(Status.PROCESSING);
    statusBox.addItem(Status.PROCESSED);
    statusBox.select(Status.WAITING);
    statusBox.setWidth("40%");

    Button updateButton = new Button("Update");
    updateButton.setWidth("40%");

    //TODO: Replace the Component.EventTypeListener by lambda expressions

    //TODO: ValueChangeListener do value type conversion. Be careful.
    locationBox.addValueChangeListener(
        event -> selectedLocation = event.getProperty().getValue().toString());
    statusBox.addValueChangeListener(
        event -> selectedStatus = event.getProperty().getValue().toString());

    clearTableButton.addClickListener(
        new Button.ClickListener() {
          public void buttonClick(Button.ClickEvent event) {
            //TODO: separate clearTable into own method.
            logTable.getContainerDataSource().removeAllItems();
            validIdList.clear();
          }
        });

    addIdButton.addClickListener(
        new Button.ClickListener() {
          public void buttonClick(Button.ClickEvent event) {
            //TODO: simplify and separate method. It is quite huge and complex.

            //TODO: is idField.getValue().equals("") the correct check?
            if (idField.getValue().equals("")) {
              //TODO: replace deprecated Notification.FOOBAR by respective Foo.Bar
              // Notification.POSITION_CENTERED_TOP -> Position.TOP_CENTER
              Notification notif =
                  new Notification("Invalid QBiC ID", "", Notification.TYPE_ERROR_MESSAGE);
              notif.setDelayMsec(20000);
              notif.setPosition(Notification.POSITION_CENTERED_TOP);
              notif.show(Page.getCurrent());
            } else {
              try {
                //TODO: move service connection responsibility out of click listener
                String baseURL = serviceList.get(0).getRootUrl().toString() + "/";

                HttpClient client = HttpClientBuilder.create().build();

                //TODO: do not store values directly in user interface
                String sampleId = idField.getValue();

                //TODO: rename variable to avoid confusion with Getter naming schema
                HttpGet getSampleInfo = new HttpGet(baseURL + "samples/" + sampleId);
                getSampleInfo.setHeader("Authorization", authHeader);
                HttpResponse response = client.execute(getSampleInfo);

                //TODO: add error handling and reporting to JSON parsing
                ObjectMapper mapper = new ObjectMapper();
                Sample sample = mapper.readValue(response.getEntity().getContent(), Sample.class);

                //TODO: this can be done in the UI. Does probably not need to move
                logTable.addRow(
                    sampleId,
                    sample.getCurrentLocation().getName(),
                    sample.getCurrentLocation().getStatus().toString());
                //TODO: look into this validIdList business. What is it for? How is it used?
                validIdList.add(sampleId);

              } catch (Exception E) { // IOException

                //TODO: remove out stream output.
                System.out.println("api exception********");

                //TODO: move notification handling on the UI into a separate method.
                // This code exists multiple times already.
                Notification notif =
                    new Notification(
                        "Invalid QBiC ID: " + idField.getValue(),
                        "",
                        Notification.TYPE_ERROR_MESSAGE);
                notif.setDelayMsec(20000);
                notif.setPosition(Notification.POSITION_CENTERED_TOP);
                notif.show(Page.getCurrent());
              }
            }
          }
        });

    updateButton.addClickListener(
        new Button.ClickListener() {
          public void buttonClick(Button.ClickEvent event) {

            //TODO: which check is the correct one?
            //TODO: replace deprecated values.
            // if (idField.getValue().equals("")){
            if ((validIdList.size() == 0) || (selectedLocation.equals(""))) {
              Notification notif =
                  new Notification(
                      "Invalid Information",
                      "Can not update, add valid IDs and fill all information",
                      Notification.TYPE_ERROR_MESSAGE);
              notif.setDelayMsec(20000);
              notif.setPosition(Notification.POSITION_CENTERED_TOP);
              notif.show(Page.getCurrent());

            } else {

              try {
                //TODO: remove system.out prints
                System.out.println("++++++++ selected loc: " + selectedLocation);

                System.out.println("iterating over valid ids...........................");

                String baseURL = serviceList.get(0).getRootUrl().toString() + "/";

                HttpClient client = HttpClientBuilder.create().build();
                //TODO: validIdList seems to contain Ids of samples to be updated
                for (String validId : validIdList) {
                  System.out.println("id: " + validId);

                  // first update location
                  String sampleId = validId;

                  HttpPost post =
                      new HttpPost(
                          baseURL
                              + "samples/"
                              + sampleId
                              + "/currentLocation/"); // we also need to transfer a (known!) location object (as json)
                  post.setHeader("Authorization", authHeader);
                  //TODO: clean up JSON mapping
                  ObjectMapper mapper = new ObjectMapper();

                  Location newLocation = locationMap.get(selectedLocation);
                  //TODO: do not parse the date directly from the UI and use it for update
                  newLocation.setArrivalDate(dateInput.getValue());
                  newLocation.setStatus(Status.WAITING);

                  String json = mapper.writeValueAsString(newLocation);
                  HttpEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
                  post.setEntity(entity);
                  HttpResponse response = client.execute(post);

                  String result = EntityUtils.toString(response.getEntity());

                  //TODO: remove System.out.prinln() statements
                  System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
                  System.out.println(result);

                  // update the sample status
                  //TODO: replace by interface for service connection
                  HttpPut put =
                      new HttpPut(
                          baseURL + "samples/" + sampleId + "/currentLocation/" + selectedStatus);
                  put.setHeader("Authorization", authHeader);
                  response = client.execute(put);

                  result = EntityUtils.toString(response.getEntity());
                  //TODO: remove System.out.println()
                  System.out.println(result);
                }

                //TODO: replace deprecated notification settings
                Notification notif =
                    new Notification("ID(s) Updated", "", Notification.TYPE_WARNING_MESSAGE);
                notif.setDelayMsec(20000);
                notif.setPosition(Notification.POSITION_CENTERED_TOP);
                notif.show(Page.getCurrent());

                // clear sample list
                //TODO: clearing the sample list should be done in a separate method
                logTable.getContainerDataSource().removeAllItems();
                validIdList.clear();
                //TODO remove Syste.out.println() statement
                System.out.println(
                    "++++++++####################" + validIdList.size());

                //TODO: why is this needed? What is achieved by clearing the locationMap?
                // clear locations
                locationMap.clear();
                selectedLocation = "";

                locationBox.setEnabled(false);

                //TODO: remove System.out.println() statement
                System.out.println("--- flag x2");

                locationBox
                    .removeAllItems(); // breaks somehow, look for a way to clear this combobox
                                       // without this call
                //TODO: here a bug seems to exist?

              } catch (Exception E) { // IOException
                //TODO: improve exception handling.
                //TODO: remove System.out.println() statement
                System.out.println("**********************************");
                System.out.println(E.toString());
              }
            }
          }
        });

    locationsButton.addClickListener(
        new Button.ClickListener() {
          public void buttonClick(Button.ClickEvent event) {

            //TODO: is this the correct check?
            if (rpEmail.getValue().equals("")) {
              //TODO: why this empty code block?
            } else {

              try {

                //TODO: replace parsing of important information out of a Label
                String contactEmail = rpEmail.getValue();

                //TODO: replace with service connection interface
                String baseURL = serviceList.get(0).getRootUrl().toString() + "/";

                HttpClient client = HttpClientBuilder.create().build();

                HttpGet getLocations = new HttpGet(baseURL + "locations/" + contactEmail);
                getLocations.setHeader("Authorization", authHeader);
                HttpResponse response = client.execute(getLocations);
                ObjectMapper mapper = new ObjectMapper();
                List<LinkedHashMap> LocationList =
                    mapper.readValue(response.getEntity().getContent(), List.class);

                locationBox.setEnabled(true);
                //TODO: what is done here? Why are the items removed?
                locationBox.removeAllItems();

                locationMap.clear();

                //TODO: Where to move the object parsing to?
                for (LinkedHashMap loc : LocationList) {

                  Location contactLocation = new Location();
                  Address contactAddress = new Address();
                  contactAddress.setAffiliation(
                      ((LinkedHashMap) loc.get("address")).get("affiliation").toString());
                  contactAddress.setStreet(
                      ((LinkedHashMap) loc.get("address")).get("street").toString());
                  contactAddress.setZipCode(
                      Integer.parseInt(
                          ((LinkedHashMap) loc.get("address")).get("zip_code").toString()));
                  contactAddress.setCountry(
                      ((LinkedHashMap) loc.get("address")).get("country").toString());

                  contactLocation.setAddress(contactAddress);
                  contactLocation.setResponsibleEmail(contactEmail);
                  contactLocation.setResponsiblePerson(loc.get("responsible_person").toString());
                  //TODO: do not use a constructor within this method
                  contactLocation.setArrivalDate(new Date()); // take NOW for now
                  contactLocation.setStatus(Status.WAITING);
                  contactLocation.setName(loc.get("name").toString());

                  locationMap.put(contactLocation.getName(), contactLocation);
                  locationBox.addItem(contactLocation.getName());
                }

              } catch (Exception E) { // IOException
                //TODO: improve exception handling
                //TODO: remove System.out.println() statements
                System.out.println("**********************************");
                System.out.println(E.toString());
              }
            }
          }
        });

    //TODO: here some UI composition takes place
    panelContentA.addComponent(idField);
    panelContentA.addComponent(addIdButton);
    panelContentA.addComponent(idFileUpload);

    panelContentA.addComponent(logTable);
    panelContentA.addComponent(clearTableButton);

    panelContentB.addComponent(rpText);
    panelContentB.addComponent(rpEmail);
    panelContentB.addComponent(locationsButton);

    panelContentB.addComponent(locationBox);
    panelContentB.addComponent(dateInput);
    panelContentB.addComponent(statusBox);
    panelContentB.addComponent(updateButton);

    mainLayout.addComponent(panelContentA);
    mainLayout.addComponent(panelContentB);

    //TODO: finish creating the queryPanel
    queryPanel.setContent(mainLayout);
    return queryPanel;
  }

  //TODO: separate visual and internal representation of the data
  private void addIdToTable(String sampleId, Grid logTable) {

    try {
      //TODO: replace by use of service interface
      String baseURL = serviceList.get(0).getRootUrl().toString() + "/";

      HttpClient client = HttpClientBuilder.create().build();

      HttpGet getSampleInfo = new HttpGet(baseURL + "samples/" + sampleId);
      getSampleInfo.setHeader("Authorization", authHeader);
      HttpResponse response = client.execute(getSampleInfo);

      ObjectMapper mapper = new ObjectMapper();
      Sample sample = mapper.readValue(response.getEntity().getContent(), Sample.class);
      // System.out.println(sample);

      logTable.addRow(
          sampleId,
          sample.getCurrentLocation().getName(),
          sample.getCurrentLocation().getStatus().toString());

      validIdList.add(sampleId);

    } catch (Exception E) { // IOException
      //TODO: replace System.out.println() by Logger
      System.out.println("Error at addIdToTable()");

      // Notification.show("Invalid QBiC ID");
      //TODO: replace by notification method
      Notification notif =
          new Notification("Invalid QBiC ID: " + sampleId, "", Notification.TYPE_ERROR_MESSAGE);
      notif.setDelayMsec(10000);
      notif.setPosition(Notification.POSITION_CENTERED_TOP);
      notif.show(Page.getCurrent());
    }
  }
}
