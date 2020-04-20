package life.qbic.portal.sampletracking.trackinginformation.query.sample

import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample
import life.qbic.openbis.openbisclient.OpenBisClient
import life.qbic.portal.sampletracking.trackinginformation.query.OpenbisAuthorizationException
import life.qbic.portal.sampletracking.trackinginformation.query.OpenbisEmptyProjectException
import life.qbic.portal.sampletracking.trackinginformation.query.SampleTrackingQueryDataSource
import life.qbic.portal.sampletracking.trackinginformation.query.SampleTrackingQueryException
import life.qbic.portal.sampletracking.trackinginformation.query.locations.QueryAvailableLocationsInput
import life.qbic.portal.sampletracking.trackinginformation.query.locations.QueryAvailableLocationsOutput

@Log4j2
class QuerySample implements QuerySampleInput {

  final OpenBisClient openbisClient
  final SampleTrackingQueryDataSource sampleTrackingDataSource
  final Set<String> openbisSpaces

  private QuerySampleOutput querySampleOutput

  private QuerySample() {
    // Avoids accidental default constructor call from within the class
    throw new AssertionError()
  }

  QuerySample(SampleTrackingQueryDataSource dataSource, QuerySampleOutput querySampleOutput, OpenBisClient openbisClient, Set<String> spaces) {
    this.sampleTrackingDataSource = dataSource
    this.querySampleOutput = querySampleOutput
    this.openbisClient = openbisClient;
    this.openbisSpaces = spaces;
  }

  private void userIsAuthorized(String sampleId) {
    List<ch.systemsx.cisd.openbis.generic.shared.api.v1.dto.Sample> samples =
        openbisClient.getParentsBySearchService(sampleId)
    if (samples.isEmpty()) {
      throw new OpenbisEmptyProjectException("User tried searching for $sampleId, but related project does not have samples in openBIS")
    }
    if (!openbisSpaces.contains(samples.get(0).getSpaceCode())) {
      throw new OpenbisAuthorizationException("User tried searching for $sampleId, but user is not allowed to view this project.")
    }
  }

  @Override
  def querySampleById(String sampleId) {
    try {
      userIsAuthorized(sampleId)

      Location sampleLocation = sampleTrackingDataSource.currentSampleLocation(sampleId)
      Sample sample = new Sample()
      sample.setCurrentLocation(sampleLocation)
      sample.setCode(sampleId)
      this.querySampleOutput.publishSample(sample)
    } catch (SampleTrackingQueryException e) {
      log.error("Query ${sampleId} failed.", e)
      // We pass the original error message with a generic notification to the output port
      this.querySampleOutput.invokeOnError "Could not retrieve information for sample $sampleId. ${e.message}"
    } catch (OpenbisAuthorizationException e) {
      log.error(e)
      this.querySampleOutput.invokeOnError "Could not retrieve information for sample $sampleId. You are not part of this project space."
    } catch (OpenbisEmptyProjectException e) {
      log.error(e)
      this.querySampleOutput.invokeOnError "Could not retrieve information for sample $sampleId. Sample not found."
    }
  }
}
