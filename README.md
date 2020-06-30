# HELLO WORLD!
This project demonstrates AWS infrastructure as code: scalable EC2 instances. The project is built using [Spring boot](https://spring.io/projects/spring-boot) - java framework and [AWS java SDK 2](https://docs.aws.amazon.com/sdk-for-java/v2/developer-guide/welcome.html).

We create a scalable infrastructure comprising of a load balancer and multiple instances. The launched instances run a [Hamster League app](https://github.com/ryanmurakami/hbfl) by [Ryan Lewis](https://github.com/ryanmurakami). 
I followed his tutorials on pluralsight: [AWS Developer: Designing and Developing](https://app.pluralsight.com/library/courses/aws-developer-designing-developing/table-of-contents), module 3 & 4.

#### INSTALLATION
Copy and populate the following values in your application.yml
```yaml

aws:
  accessKeyId: 
  secretKey: 
  region: 
  vpc: 
  subnets: 
  profile: 
  keyName: 
  secGrpName: 
```

#### RUN
To launch the AWS resources, the app is triggered by a GET request to **http:localhost:<PORT>/launch**.

#### LICENSE
Copyright 2020 
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.