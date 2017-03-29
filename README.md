# StanfordNlpServer

## How to Run Stanford NLP Server

### Install Docker

```shell-script
sudo apt-get install apt-transport-https ca-certificates curl software-properties-common
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu trusty stable"
sudo apt-get update
sudo apt-get install docker-ce
```

### Run the Server

In the foreground:

```shell-script
docker run -it --rm -p 8080:8080 gliacloud/stanfordnlpserver
```

Or in the background:

```shell-script
docker run -d -p 8080:8080 gliacloud/stanfordnlpserver
```
