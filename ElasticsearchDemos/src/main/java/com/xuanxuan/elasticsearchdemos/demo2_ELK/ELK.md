## Elk
Elk 是由 ElasticStack 提供的强大日志收集框架，由四个部分组成:
FileBeat => Logstash => Elasticsearch => Kibana
<br>
- FileBeat: 日志收集器
- Logstash: 日志过滤与处理，比如过滤日志级别
- Elasticsearch: 数据存储
- Kibana: 数据展示

<br>
<br>

### 启动每个组件
- Elasticsearch: 在 bin 目录下直接启动 
> 命令: `elasticsearch`，可以在本地 9200 端口查看启动状态
- Kibana: 在 bin 目录下直接启动
> 命令: `kibana.bat`，可以在本地 5601 端口查看启动状态
- Logstash: 在 bin 目录下执行命令，对应的文件需要自己指定，可以配置自己想要的规则(见下文)
> 命令: `logstash -f ..\config\mylogtask.conf`，可以在本地 9600 端口查看启动状态，但是 LogStash 对应的端口为 5044
- FileBeats: 在 filebeat 目录下启动，需要更改配置文件(见下文)
> 命令: `filebeat.exe -e -c filebeat.yml`

<br>
<br>

### LogStash
包含3个部分，包括 input, filter, output，修改 conf 文件:

其中 filter 为过滤规则，请按照自己需要进行编写；

```json
input {
  beats {
    port => 5044
  }
}

filter {
  grok {
    match => {
      "message" => "(?<name>\w+)|(?<age>\d+)"
    }
  }
}

output {
  elasticsearch {
    hosts => ["http://localhost:9200"]
    index => "%{[@metadata][beat]}-%{[@metadata][version]}-%{+YYYY.MM.dd}"
  }
}

```

<br>
<br>

### FileBeat
filebeat.yml 配置文件:
1. output 改成 logstash，把 elasticsearch 注释掉;
2. inputs 指明 type 为 log，且打开 enabled;
3. inputs.path 指明日志文件路径，支持正则表达式；

下面展示一下省略版的，请自行在源文件上进行对应的更改，下面这个没法直接复制使用，只是指明要改哪！
```yaml
# ============================== Filebeat inputs ===============================

filebeat.inputs:
  type: log

  # Unique ID among all inputs, an ID is required.
  id: my-filestream-id

  # Change to true to enable this input configuration.
  enabled: true

  # Paths that should be crawled and fetched. Glob based paths.
  paths:
    - d:\TechDemo\ElasticsearchDemos\logs\testELK.log
    #- /var/log/*.log
    #- c:\programdata\elasticsearch\logs\*

# ================================== Outputs ===================================

# ---------------------------- Elasticsearch Output ----------------------------
#output.elasticsearch:
  #hosts: ["localhost:9200"]

# ------------------------------ Logstash Output -------------------------------
output.logstash:
  hosts: ["localhost:5044"]
```



### 测试

在 `log` 文件所在文件夹下启动 cmd 输入命令 `echo xx.log >> "xuanxuan|18"` 应该能收到消息，在 Kibana 中左侧栏的 Analytics 下有一个 Discover，可以设置看板并且看到结果，此时就配置成功啦！