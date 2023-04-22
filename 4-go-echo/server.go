package main

import (
	"github.com/labstack/echo/v4"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
)

func main() {
	e := echo.New()

	db, _ := gorm.Open(sqlite.Open("gorm.db"), &gorm.Config{})
	err := db.AutoMigrate(&Product{}, &Cart{})

	if err != nil {
		return
	}

	registerProductController(e, db)

	registerCartController(e, db)

	e.Logger.Fatal(e.Start(":1323"))
}
