export HADOOP_HOME="/opt/bigdata/hadoop-3.3.0"
export HADOOP_USER="root"
export HADOOP_COMMON_HOME=$HADOOP_HOME
export HADOOP_MAPRED_HOME=$HADOOP_HOME
export HADOOP_HDFS_HOME=$HADOOP_HOME
export HADOOP_YARN_HOME=$HADOOP_HOME
export HADOOP_LIBEXEC_HOME=$HADHADOOP_HOMEOOP_HOME/libexec

export HADOOP_INSTALL=$HADOOP_HOME
export HADOOP_COMMON_LIB_NATIVE_DIR=$HADOOP_HOME/lib/native
export HADOOP_LIBEXEC_DIR=$HADOOP_HOME/libexec
export JAVA_LIBRARY_PATH=$HADOOP_HOME/lib/native:$JAVA_LIBRARY_PATH
export HADOOP_CONF_DIR=$HADOOP_HOME/etc/hadoop
export JAVA_LIBRARY_PATH=/opt/bigdata/hadoop-3.3.0/lib/native
export HADOOP_OPTS="-Djava.library.path=$HADOOP_HOME/lib"

export HDFS_NAMENODE_USER="root"
export HDFS_DATANODE_USER="root"
export HDFS_NAMENODE_USER="root"
export HDFS_SECONDARYNAMENODE_USER="root"

export YARN_HOME=$HADOOP_HOME
export YARN_RESOURCEMANAGER_USER="root"
export YARN_NODEMANAGER_USER="root"

export PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin


