# A Fog Computing Prototype

## Introduction
*A Fog Computing Prototype* is an infrastructure for early detection of epilepsy seizures using EEG timeseries data consisting of three layers: IoT devices and sensors, a Fog layer, and the Cloud. The goal of the project is to simulate streaming data generated at the edge layer, and compare the performance in terms of both accuracy and efficiency with sending the data over to the cloud for further analytics. The processing at the edge is fast, simple, and efficient, while the cloud analytics are more accurate, though at the cost of increased bandwidth and response time. Please refer to the [project report](report/Report.pdf) for more information on the theory, the methodology, and the experimentation results.

### The Dataset
The dataset is taken from [UCI Machine Learning Repository](https://archive.ics.uci.edu/ml/datasets/Epileptic+Seizure+Recognition).

## Installation

* The edge tier is implemented using [Apache Edgent](https://edgent.apache.org/) in Java. [IntelliJ Idea](https://www.jetbrains.com/idea/) was used for development of this tier. 
* The cloud tier is implemented using [PySpark](https://spark.apache.org/docs/latest/api/python/index.html) in Python.

For setting up and importing the project files of each tier, please refer to the user manual of the IDE of your choice.

## Usage
The two tiers are not connected to each other. Therefore, you can run each one separately, and perform the analytics at each layer. We may consider connecting the two tiers as a future work.

## Contributing
We do not plan to work on the project anymore, except for occasional bug fixes or major issues. For these, please open an issue first to discuss what you would like to change, and we will try to resolve it as soon as possible. Otherwise, feel free to fork the project and make your own changes.

## License
[MIT](https://choosealicense.com/licenses/mit/)