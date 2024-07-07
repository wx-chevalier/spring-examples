## 安装 Docker
从 2017 年 3 月开始 docker 在原来的基础上分为两个分支版本: Docker CE 和 Docker EE。

Docker CE 即社区免费版，Docker EE 即企业版，强调安全，但需付费使用。

本文介绍 Docker CE 的安装使用。

移除旧的版本：

```
$ sudo yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-selinux \
                  docker-engine-selinux \
                  docker-engine
```

安装一些必要的系统工具：

```
sudo yum install -y yum-utils device-mapper-persistent-data lvm2
```

添加软件源信息：

```
sudo yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
```

更新 yum 缓存：

```
sudo yum makecache fast
```

安装 Docker-ce：

```
sudo yum -y install docker-ce
```

查看已安装docker版本
```
docker version
```

启动 Docker 后台服务

```
sudo systemctl start docker
```

开机启动

```
systemctl enable docker
```


## 镜像加速
鉴于国内网络问题，后续拉取 Docker 镜像十分缓慢，我们可以需要配置加速器来解决，推荐使用的是docker官方推荐的中国镜像地址：https://registry.docker-cn.com

新版的 Docker 使用 `/etc/docker/daemon.json`（Linux，没有请新建）。

请在该配置文件中加入（没有该文件的话，请先建一个）：
```javascript
{
  "registry-mirrors": ["https://registry.docker-cn.com"]
}
```

重启docker

```
sudo systemctl daemon-reload
sudo systemctl restart docker
```

### 检查加速器是否生效

配置加速器之后，如果拉取镜像仍然十分缓慢，请手动检查加速器配置是否生效，在命令行执行 `docker info`，如果从结果中看到了如下内容，说明配置成功。
```
Registry Mirrors:
 https://registry.docker-cn.com/
```
