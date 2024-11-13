## DSL
记录调试时可能用到的 DSL 相关语法，方便在 Kibana 看板里进行调试。根本不需要去背 DSL 语法，要用直接去问 AI 就行。

### 简单查询
```json
GET post_v1           // 查询表结构 Mapping
GET post_v1/_doc/1    // 根据 id 查询

GET post/_search      // 查询所有的数据
{
  "query": {
    "match_all": { }
  }
}
```

### DSL 复杂查询
一般先写对应的 DSL 测试成功之后，再去写 ESRestTemplate 对应代码。
```json
{
  "query": {
    // 组合下列条件
    "bool": {
      // must_not: 不包含
      "must_not": [
        {
          "match": {
            "title": "xxx"
          }
        },
      ],
      // should: 包含
      "should": [
        {
          // match: 模糊查询
          "match": {
            "title": "xxx"
          }
        },
        {
          "match": {
            "desc": "xxx"
          }
        }
      ],
      "filter": [
        {
          // term: 精确查询
          "term": {
            "isDelete": 0
          }
        },
        {
          "term": {
            "id": xxx
          }
        },
        {
          "term": {
            "tags": "xxx"
          }
        },
        {
          "term": {
            "tags": "xxx"
          }
        }
      ],
      // should 只要满足该数量的条件即可匹配
      "minimum_should_match": 0
    }
  },
  // 分页 from, size
  "from": 0, 
  "size": 5,
  // 查询字段
  "_source": ["name", "_createTime", "desc", "reviewStatus", "priority", "tags"],
  "sort": [
    {
      "priority": {
        "order": "desc"
      }
    },
    {
      "_score": {
        "order": "desc"
      }
    },
    {
      "publishTime": {
        "order": "desc"
      }
    }
  ]
}

```

### 建索引(建表)
```json
POST post_v1
{
  // 别名，可以按照 post_v1 拿到表，也可以 post 拿到表
  "aliases": {  
    "post": {}
  },
  "mappings": {
    "properties": {
      "title": {
        "type": "text",
        // 存储用的分词器
        "analyzer": "ik_max_word",  
        // 查询用的分词器
        "search_analyzer": "ik_smart",
        // text 类型数据会分词
        // 可以指定 keyword 使其也支持精确查询
        "fields": {
          "keyword": {
            "type": "keyword",
            "ignore_above": 256
          }
        }
      },
      "content": {
        "type": "text",
        "analyzer": "ik_max_word",
        "search_analyzer": "ik_smart",
        "fields": {
          "keyword": {
            "type": "keyword",
            "ignore_above": 256
          }
        }
      },
      "tags": {
        "type": "keyword"
      },
      "userId": {
        "type": "keyword"
      },
      "createTime": {
        "type": "date"
      },
      "updateTime": {
        "type": "date"
      },
      "isDelete": {
        "type": "keyword"
      }
    }
  }
}
