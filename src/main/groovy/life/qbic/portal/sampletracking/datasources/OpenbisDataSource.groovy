package life.qbic.portal.sampletracking.datasources

import ch.ethz.sis.openbis.generic.asapi.v3.dto.sample.Sample
import groovy.util.logging.Log4j2
import life.qbic.openbis.openbisclient.OpenBisClient
import life.qbic.portal.sampletracking.trackinginformation.query.OpenbisAuthorizationException
import life.qbic.portal.sampletracking.trackinginformation.query.SampleNotInOpenbisException
import life.qbic.portal.utils.ConfigurationManager

@Log4j2
class OpenbisDataSource implements SampleManagementDataSource {

  private OpenBisClient openbis;
  private Set<String> userSpaces = new HashSet<>()

  public OpenbisDataSource(ConfigurationManager configManager, String userID) {
    log.info("Trying to connect to openBIS")
    openbis = new OpenBisClient(configManager.getDataSourceUser(), configManager.getDataSourcePassword(), configManager.getDataSourceUrl())
    openbis.login()
    log.info("Fetching user spaces for " + userID)
    userSpaces.addAll(openbis.getUserSpaces(userID))
  }

  @Override
  public void checkUserAuthorization(String sampleId) {
    List<Sample> res = openbis.searchSampleByCode(sampleId)
    if(res.size() == 0) {
      throw new SampleNotInOpenbisException("User tried searching for $sampleId, but sample does not exist in openBIS. Unable to verify authorization.")
    }

    if(!userSpaces.contains(res.get(0).getSpace()) {
      throw new OpenbisAuthorizationException("User tried searching for $sampleId, but user is not allowed to view this project.")
    }
  }
}