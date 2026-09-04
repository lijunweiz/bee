# bee

> 一个基于 Java、Spring Boot 和 Aviator 的轻量级规则引擎。

bee 面向规则配置、变量管理、数据源管理和表达式执行场景，提供从管理端配置到核心规则执行的一组基础能力。规则内容可以用 JSON 结构保存，并可在 `bee-core` 中适配为 Aviator 表达式执行。

## Features

- **规则配置**：支持规则集创建、更新、查询、规则树展示和规则详情维护。
- **表达式执行**：基于 Aviator 封装表达式编译、缓存和执行能力。
- **规则 JSON 适配**：支持将 `RuleContent` JSON 转换为 Aviator 表达式。
- **递归条件解析**：支持 `AND`、`OR`、`NOT` 条件组嵌套。
- **常用运算符**：支持数字比较、字符串相等、`in`、`notIn` 等规则运算符。
- **动作结果映射**：规则命中后返回 action 对应的结果 Map。
- **自定义函数**：支持动态编译并注册 Aviator 自定义函数。
- **管理端接口**：提供规则、变量、模型、字典、数据源、表达式函数等管理接口。

## Tech Stack

- Java 11
- Spring Boot
- Spring Cloud
- MyBatis
- MySQL
- Druid
- Aviator
- Fastjson
- JSONPath

## Modules

| Module | Description |
| --- | --- |
| `bee-core` | 核心模块，包含规则执行、Aviator 封装、规则定义、数据源访问、JSONPath 解析、动态编译和表达式适配。 |
| `bee-common` | 通用模块，包含统一返回结果、通用注解、常量和公共依赖封装。 |
| `bee-persistence` | 持久化模块，包含实体、查询条件、Mapper 和 MyBatis XML。 |
| `bee-service` | 业务服务模块，承接接口层和持久化层之间的业务逻辑。 |
| `bee-turn` | DTO 模块，定义请求和响应数据结构。 |
| `bee-manage` | 管理端服务，提供规则、变量、模型、字典、数据源、Aviator 函数等管理接口。 |
| `bee-api` | API 服务，提供对外接口能力。 |

## Quick Start

### Build

```bash
mvn clean package
```

### Test Core Module

```bash
mvn -pl bee-core test
```

### Start Manage Service

```bash
mvn -pl bee-manage spring-boot:run
```

默认地址：

```text
http://localhost:8888
```

### Start API Service

```bash
mvn -pl bee-api spring-boot:run
```

默认地址：

```text
http://localhost:7777
```

## Rule JSON Adapter

`bee-core` 提供 `AviatorExpressionAdapter`，用于把 `RuleContent` JSON 转换为 Aviator 表达式。

示例规则：

```json
{
  "rules": [
    {
      "condition": {
        "operator": "AND",
        "children": [
          {
            "field": "age",
            "op": ">=",
            "value": "18"
          },
          {
            "field": "status",
            "op": "in",
            "value": ["NORMAL", "VIP"]
          }
        ]
      },
      "action": {
        "field": "decisionResult",
        "operator": "=",
        "value": "通过"
      }
    }
  ],
  "elseAction": {
    "field": "decisionResult",
    "operator": "=",
    "value": "拒绝"
  }
}
```

转换后表达式会按规则顺序命中。条件满足时，返回当前 action 对应的 Map：

```text
{decisionResult=通过}
```

`in` 和 `notIn` 会适配为 Aviator 的 `include(seq, element)`：

```text
status in ["NORMAL", "VIP"]    -> include(seq.list("NORMAL", "VIP"), status)
status notIn ["BLACK"]         -> !(include(seq.list("BLACK"), status))
```

## Project Structure

```text
bee
├── bee-api          # 对外 API 服务
├── bee-common       # 通用能力
├── bee-core         # 规则引擎核心能力
├── bee-manage       # 管理端服务
├── bee-persistence  # 持久化层
├── bee-service      # 业务服务层
└── bee-turn         # 请求和响应 DTO
```

## License

This project is licensed under the MIT License.
