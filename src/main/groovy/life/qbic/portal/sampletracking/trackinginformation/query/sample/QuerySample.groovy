package life.qbic.portal.sampletracking.trackinginformation.query.sample

import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample
import life.qbic.openbis.openbisclient.OpenBisClient
import life.qbic.portal.sampletracking.datasources.SampleManagementDataSource
import life.qbic.portal.sampletracking.trackinginformation.query.OpenbisAuthorizationException
import life.qbic.portal.sampletracking.trackinginformation.query.SampleNotInOpenbisException
import life.qbic.portal.sampletracking.trackinginformation.query.SampleTrackingQueryDataSource
import life.qbic.portal.sampletracking.trackinginformation.query.SampleTrackingQueryException
import life.qbic.portal.sampletracking.trackinginformation.query.locations.QueryAvailableLocationsInput
import life.qbic.portal.sampletracking.trackinginformation.query.locations.QueryAvailableLocationsOutput

@Log4j2
class QuerySample implements QuerySampleInput {

  final OpenBisClient openbisClient
  final SampleTrackingQueryDataSource sampleTrackingDataSource
  final SampleManagementDataSource sampleManagementDataSource
  final Set<String> openbisSpaces

  private QuerySampleOutput querySampleOutput

  private QuerySample() {
    // Avoids accidental default constructor call from within the class
    throw new AssertionError()
  }

  QuerySample(SampleTrackingQueryDataSource dataSource, SampleManagementDataSource sampleManagementDataSource, QuerySampleOutput querySampleOutput) {
    this.sampleTrackingDataSource = dataSource
    this.querySampleOutput = querySampleOutput
    this.sampleManagementDataSource = sampleManagementDataSource;
  }

  @Override
  def querySampleById(String sampleId) {
    try {
      if (sampleManagementDataSource.isUserAuthorizedForSample(sampleId)) {
        Location sampleLocation = sampleTrackingDataSource.currentSampleLocation(sampleId)
        Sample sample = new Sample()
        sample.setCurrentLocation(sampleLocation)
        sample.setCode(sampleId)
        this.querySampleOutput.publishSample(sample)
      } else {
        throw new OpenbisAuthorizationException("User tried searching for $sampleId, but user is not allowed to view this project.")
      }
    } catch (SampleTrackingQueryException e) {
      log.error("Query ${sampleId} failed.", e)
      this.querySampleOutput.invokeOnError "Could not retrieve information for sample $sampleId."
    } catch (OpenbisAuthorizationException e) {
      log.error(e)
      this.querySampleOutput.invokeOnError "Could not retrieve information for sample $sampleId. You are not part of this project space."
    } catch (SampleNotInOpenbisException e) {
      log.error(e)
      this.querySampleOutput.invokeOnError "Could not retrieve information for sample $sampleId. Sample not found."
    }
  }
}
