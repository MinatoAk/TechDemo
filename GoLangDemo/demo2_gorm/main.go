package main

import (
	"fmt"
	"time"

	"gorm.io/driver/mysql"
	"gorm.io/gorm"
)

// 1) 表名 - 默认转化为小写并且加复数 - users
// 2) 字段名 - 默认转化为小写 - id, name, age
// 3) 时间戳字段 - 默认添加 created_at, updated_at 自动追踪时间
// 4) gorm.Model - 包含 id, created_at, updated_at, deleted_at(逻辑删除字段)
type User struct {
	UserId    uint   `gorm:"primaryKey"`
	Name      string `gorm:"size:255"`
	Age       int
	CreatedAt time.Time `gorm:"autoCreateTime"`
	UpdatedAt time.Time `gorm:"autoUpdateTime"`
}

func main() {
	// 1) 打开数据库连接
	// ------------------------ todo: 记得改成自己的用户名，密码，地址，数据库名称 ------------------------
	dataSourceName := "root:QQPWCOLA233@tcp(127.0.0.1:3306)/test_db?charset=utf8mb4&parseTime=True&loc=Local"
	db, err := gorm.Open(mysql.Open(dataSourceName), &gorm.Config{})

	if err != nil {
		panic("[X] FAILED TO CONNECT TO DATABASE")
	}

	// 2) 关闭数据库连接
	defer func() {
		sqlDB, err := db.DB()
		if err != nil {
			panic("[x] FAILED TO CLOSE DATABASE CONNECTION")
		}
		sqlDB.Close()
	}()

	// 3) 自动迁移模式: 自动创建、迁移或更新数据库表结构
	db.AutoMigrate(&User{})

	// 4) 创建一个新用户
	// 4.1) 创建单个用户
	user := User{
		UserId: 1,
		Name:   "xuanxuan1",
		Age:    1,
	}
	res := db.Create(&user)

	if res.Error != nil {
		panic(fmt.Sprintf("[x] FAILED TO CREATE USER: %v", res.Error))
	}

	// 4.2) 批量创建用户
	users := []User{
		{
			UserId: 2,
			Name:   "xuanxuan2",
			Age:    2,
		},
		{
			UserId: 3,
			Name:   "xuanxuan3",
			Age:    3,
		},
	}

	res = db.Create(users)
	// res = db.CreateInBatches(users, 2) // 可以设置批量大小，每次会开一个事务
	if res.Error != nil {
		panic(fmt.Sprintf("[x] FAILED TO CREATE USER: %v", res.Error))
	}

	// 5) 查询用户
	// var user User
	// db.First(&user, 1) // 查询 ID 为 1 的用户
	// fmt.Println(user)

	// // 更新用户
	// db.Model(&user).Update("Age", 31)

	// // 删除用户
	// db.Delete(&user, 1)
}
