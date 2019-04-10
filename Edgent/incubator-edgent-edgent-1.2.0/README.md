<!--

  Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.

-->
# Welcome to Apache Edgent!

[![Build Status](https://travis-ci.org/apache/incubator-edgent.svg?branch=develop)](https://travis-ci.org/apache/incubator-edgent)

*Apache Edgent is an effort undergoing incubation at The Apache Software Foundation (ASF), sponsored by the Incubator PMC. Incubation is required of all newly accepted projects until a further review indicates that the infrastructure, communications, and decision making process have stabilized in a manner consistent with other successful ASF projects. While incubation status is not necessarily a reflection of the completeness or stability of the code, it does indicate that the project has yet to be fully endorsed by the ASF.*

Apache Edgent is an open source programming model and runtime for edge devices that enables you to analyze data and events at the device.

Please join us by subscribing to the developer mailing list
[dev at edgent.incubator.apache.org](http://mail-archives.apache.org/mod_mbox/incubator-edgent-dev/).
To subscribe, send an email to `dev-subscribe at edgent.incubator.apache.org`.

We want to build a community around Edgent for analytics at the edge, so welcome contributions to any aspect of Edgent including:

 * Feedback from use in IoT and other device environments.
 * Support for more device environments
 * Additional connectors to edge sensors or new message hubs
 * Analytics to be executed at the edge
 * Sample applications
 * Documentation
 * Testing
 * Bug fixing
 * ...

Please *Get Involved!*

Edgent is released under the [Apache License Version 2.0](LICENSE)

# Edgent
Devices and sensors are everywhere. And more are coming online every day. You need a way to analyze all of the data coming from your devices, but it can be expensive to transmit all of the data from a sensor to your central analytics engine.

Edgent is an open source programming model and runtime for edge devices that enables you to analyze data and events at the device. When you analyze on the edge, you can:

* Reduce the amount of data that you transmit to your analytics server
* Reduce the amount of data that you store

An Edgent application uses analytics to determine when data needs to be sent to a back-end system for further analysis, action, or storage. For example, you can use Edgent to determine whether a system is running outside of normal parameters, such as an engine that is running too hot.

If the system is running normally, you don’t need to send this data to your back-end system; it’s an added cost and an additional load on your system to process and store. However, if Edgent detects an issue, you can transmit that data to your back-end system to determine why the issue is occurring or how to resolve the issue.   

Edgent enables you to shift from a continuous flow of trivial data to an intermittent trickle of meaningful data. This is especially important when the cost of communication is high, such as when using a cellular network to transmit data, or when bandwidth is limited.

The following use cases describe the primary situations in which you would use Edgent:

* *Internet of Things (IoT):* Analyze data on distributed edge devices and mobile devices to:
  * Reduce the cost of transmitting data
  * Provide local feedback at the devices
* *Embedded in an application server instance:* Analyze application server error logs in real time without impacting network traffic
* *Server rooms and machine rooms:* Analyze machine health in real time without impacting network traffic or when bandwidth is limited

## Edge devices and back-end systems
You can send data from an Edgent application to your back-end system when you need to perform analysis that cannot be performed on the edge device, such as:

* Running a complex analytic algorithm that requires more resources, such as CPU or memory, than are available on the edge device.
* Maintaining large amounts of state information about a device, such as several hours worth of state information for a patient’s
medical device.
* Correlating data from the device with data from other sources, such as:
  * Weather data
  * Social media data
  * Data of record, such as a patient’s medical history or trucking manifests
  * Data from other devices

Edgent communicates with your back-end systems through the following message hubs:

* MQTT – The messaging standard for IoT
* IBM Watson IoT Platform – A cloud-based services that provides a device model on top of MQTT
* Apache Kafka – An enterprise-level message bus
* Custom message hubs

Your back-end systems can also use analytics to interact with and control edge devices. For example:

* A traffic alert system can send an alert to vehicles that are heading towards an area where an accident occurred
* A vehicle monitoring system can reduce the maximum engine revs to reduce the chance of failure before the next scheduled service if it detects patterns that indicate a potential problem

## Runtime environments
Edgent has a language binding for Java. See [JAVA SUPPORT](JAVA_SUPPORT.md) for information about the supported Java environments.

## Getting Started
See the [Edgent website](http://edgent.incubator.apache.org/) for more information on all
aspects of Edgent including Getting Started.

For details about the Edgent sources and contributing to 
Edgent runtime development see [DEVELOPMENT.md](DEVELOPMENT.md)

# Renamed from Apache Quarks
Apache Quarks was renamed to Apache Edgent.

See the [RELEASE_NOTES](RELEASE_NOTES] for change information if
you are converting from "Apache Quarks".
