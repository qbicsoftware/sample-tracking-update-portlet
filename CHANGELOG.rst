==========
Changelog
==========

This project adheres to `Semantic Versioning <https://semver.org/>`_.


1.3.0-SNAPSHOT (2021-03-02)
---------------------------

**Added**

**Fixed**

* Selectable statuses have changed to ``[life.qbic.datamodel.samples.Status.SAMPLE_RECEIVED, life.qbic.datamodel.samples.Status.SAMPLE_QC_PASS, life.qbic.datamodel.samples.Status.SAMPLE_QC_FAIL, life.qbic.datamodel.samples.Status.LIBRARY_PREP_FINISHED]`` (`#76 <https://github.com/qbicsoftware/sample-tracking-update-portlet/pull/76>`_)

**Dependencies**

* Bump ``maven-site-plugin:3.7.1`` -> ``3.9.1`` (`#66 <https://github.com/qbicsoftware/sample-tracking-update-portlet/pull/66>`_)

* Bump ``portlet-parent-pom:3.1.3`` -> ``3.1.4`` (`#68 <https://github.com/qbicsoftware/sample-tracking-update-portlet/pull/68>`_)

* Bump ``httpclient:4.5.9`` -> ``4.5.13`` (`#67 <https://github.com/qbicsoftware/sample-tracking-update-portlet/pull/67>`_)

* Bumps ``spock-core:2.0-M4-groovy-3.0`` -> ``2.0-M5-groovy-3.0`` (`#77 <https://github.com/qbicsoftware/sample-tracking-update-portlet/pull/77>`_)

* Bump ``maven-project-info-reports-plugin:3.0.0`` -> ``3.1.1`` (`#64 <https://github.com/qbicsoftware/sample-tracking-update-portlet/pull/64>`_)

* Bump ``core-utils-lib:1.7.0`` -> ``1.7.1`` (`#63 <https://github.com/qbicsoftware/sample-tracking-update-portlet/pull/63>`_)

* Bump ``gmavenplus-plugin:1.12.0`` -> ``1.12.1`` (`#61 <https://github.com/qbicsoftware/sample-tracking-update-portlet/pull/61>`_)

* Bump ``openbis-client-lib:1.4.0`` -> ``1.5.0`` (`#60 <https://github.com/qbicsoftware/sample-tracking-update-portlet/pull/60>`_)

* Bump `jackson-databind <https://github.com/FasterXML/jackson>`_ ``:2.9.10.7`` -> ``2.12.2`` (`#71 <https://github.com/qbicsoftware/sample-tracking-update-portlet/pull/71>`_)

* Bump ``data-model-lib:2.0.0`` -> ``2.4.0`` (`#74 <https://github.com/qbicsoftware/sample-tracking-update-portlet/pull/74>`_,`#75 <https://github.com/qbicsoftware/sample-tracking-update-portlet/pull/75>`_)

**Deprecated**


1.2.0 (2021-03-02)
------------------

**Added**

* Add documentation on portlet architecture (`#33 <https://github.com/qbicsoftware/sample-tracking-update-portlet/pull/33>`_)

* Add documentation on how to use the portlet (`#47 <https://github.com/qbicsoftware/sample-tracking-update-portlet/pull/47>`_)

* Add support for sample selection from CSV files (`#44 <https://github.com/qbicsoftware/sample-tracking-update-portlet/pull/44>`_)

* Add `qube-cli <https://github.com/qbicsoftware/qube-cli>`_ support

* Add arrival time information column to sample description (`#54 <https://github.com/qbicsoftware/sample-tracking-update-portlet/pull/54>`_)

* Add improved view layout (`#56 <https://github.com/qbicsoftware/sample-tracking-update-portlet/pull/56>`_)

**Fixed**

* Fixed (`#34 Sample update deletes history <https://github.com/qbicsoftware/sample-tracking-update-portlet/issues/34>`_)

* Fixed arrival time selection behaves consistent now (`#54 <https://github.com/qbicsoftware/sample-tracking-update-portlet/pull/54>`_)

* Fixed wrong default status being selected if no selection was made by the user (`#55<https://github.com/qbicsoftware/sample-tracking-update-portlet/pull/55>`_)


**Dependencies**

* Add ``com.beust:jcommander:jar:1.78``
* Add ``com.damnhandy:handy-uri-templates:jar:2.1.8``
* Add ``com.github.everit-org.json-schema:org.everit.json.schema:jar:1.12.1``
* Add ``com.github.javaparser:javaparser-core:jar:3.17.0``
* Add ``com.google.re2j:re2j:jar:1.3``
* Add ``commons-collections:commons-collections:jar:3.2.2``
* Add ``commons-digester:commons-digester:jar:1.8.1``
* Add ``commons-validator:commons-validator:jar:1.6``
* Add ``jline:jline:jar:2.14.6``
* Add ``joda-time:joda-time:jar:2.10.2``
* Add ``life.qbic:openbis-api:jar:18.06.2``
* Add ``life.qbic:openbis-core:jar:18.06.2``
* Add ``org.apache.ant:ant-antlr:jar:1.10.9``
* Add ``org.apache.ant:ant-junit:jar:1.10.9``
* Add ``org.apache.ant:ant-launcher:jar:1.10.9``
* Add ``org.apache.ant:ant:jar:1.10.9``
* Add ``org.apiguardian:apiguardian-api:jar:1.1.0``
* Add ``org.codehaus.groovy:groovy-all:pom:3.0.7``
* Add ``org.hamcrest:hamcrest:jar:2.2``
* Add ``org.json:json:jar:20190722``
* Add ``org.junit.jupiter:junit-jupiter-api:jar:5.7.0``
* Add ``org.junit.jupiter:junit-jupiter-engine:jar:5.7.0``
* Add ``org.junit.platform:junit-platform-commons:jar:1.7.0``
* Add ``org.junit.platform:junit-platform-engine:jar:1.7.0``
* Add ``org.junit.platform:junit-platform-launcher:jar:1.7.0``
* Add ``org.testng:testng:jar:7.3.0``

* Upgrade ``com.fasterxml.jackson.core:jackson-annotations:jar:2.9.9`` -> ``2.12.0``
* Upgrade ``com.fasterxml.jackson.core:jackson-databind:jar:2.9.10.4`` -> ``2.9.10.7``
* Upgrade ``com.fasterxml.jackson.datatype:jackson-datatype-jdk8:jar:2.9.8`` -> ``2.9.9``
* Upgrade ``com.fasterxml.jackson.datatype:jackson-datatype-jsr310:jar:2.9.8`` -> ``2.9.9``
* Upgrade ``com.google.code.findbugs:jsr305:jar:1.3.9`` -> ``3.0.2``
* Upgrade ``io.micronaut:micronaut-http-client:1.1.2`` -> ``1.2.11``
* Upgrade ``io.reactivex.rxjava2:rxjava:jar:2.2.6`` -> ``2.2.10``
* Upgrade ``junit:junit:jar:4.12`` -> ``4.13.1``
* Upgrade ``life.qbic:core-utils-lib:jar:1.3.0`` -> ``1.7.0``
* Upgrade ``life.qbic:data-model-lib:jar:1.7.0`` -> ``2.0.0``
* Upgrade ``life.qbic:openbis-client-lib:jar:1.3.0`` -> ``1.4.0``
* Upgrade ``life.qbic:portal-utils-lib:jar:2.2.0`` -> ``2.2.1``
* Upgrade ``life.qbic:xml-manager-lib:jar:1.5.0`` -> ``1.6.0``
* Upgrade ``org.apache.logging.log4j:log4j-api:jar:2.11.0`` -> ``2.13.2``
* Upgrade ``org.apache.logging.log4j:log4j-core:jar:2.11.0`` -> ``2.13.2``
* Upgrade ``org.codehaus.groovy:groovy-json:jar:2.5.7`` -> ``3.0.7``
* Upgrade ``org.codehaus.groovy:groovy-sql:jar:2.5.7`` -> ``3.0.7``
* Upgrade ``org.slf4j:slf4j-api:jar:1.7.25`` -> ``1.7.26``
* Upgrade ``org.spockframework:spock-core:jar:1.3-groovy-2.5`` -> ``2.0-M4-groovy-3.0``
* Upgrade ``org.yaml:snakeyaml:jar:1.23`` -> ``1.24``

* Remove ``com.github.stefanbirkner:system-rules:jar:1.17.2``
* Remove ``com.google.errorprone:error_prone_annotations:jar:2.1.3``
* Remove ``com.google.guava:guava:jar:23.4-android``
* Remove ``com.google.j2objc:j2objc-annotations:jar:1.1``
* Remove ``com.google.truth:truth:jar:0.40``
* Remove ``com.googlecode.java-diff-utils:diffutils:jar:1.3.0``
* Remove ``commons-codec:commons-codec:jar:1.11``
* Remove ``info.picocli:picocli:jar:3.7.0``
* Remove ``life.qbic.openbis:openbis_api:jar:3-S253.0``
* Remove ``net.bytebuddy:byte-buddy-agent:jar:1.8.5``
* Remove ``net.bytebuddy:byte-buddy:jar:1.8.5``
* Remove ``org.codehaus.groovy:jar:2.5.4``
* Remove ``org.codehaus.mojo:animal-sniffer-annotations:jar:1.14``
* Remove ``org.hamcrest:hamcrest-all:jar:1.3``
* Remove ``org.javassist:javassist:jar:3.22.0-CR2``
* Remove ``org.mockito:mockito-core:jar:2.18.3``
* Remove ``org.objenesis:objenesis:jar:2.6``
* Remove ``org.powermock:powermock-api-mockito2:jar:2.0.0-beta.5``
* Remove ``org.powermock:powermock-api-support:jar:2.0.0-beta.5``
* Remove ``org.powermock:powermock-core:jar:2.0.0-beta.5``
* Remove ``org.powermock:powermock-module-junit4-common:jar:2.0.0-beta.5``
* Remove ``org.powermock:powermock-module-junit4:jar:2.0.0-beta.5``
* Remove ``org.powermock:powermock-reflect:jar:2.0.0-beta.5``


**Deprecated**

* ``life.qbic.portal.sampletracking.trackinginformation.update.SampleTrackingUpdateInput#setSampleStatus`` is now deprecated. Please use the method ``SampleTrackingUpdateInput#setCurrentSampleLocation`` to also update the status. (`#35 <https://github.com/qbicsoftware/sample-tracking-update-portlet/pull/35>`_)