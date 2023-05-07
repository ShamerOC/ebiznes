package main

import (
	"github.com/labstack/echo/v4"
	"github.com/labstack/echo/v4/middleware"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
	"net/http"
)

func main() {
	e := echo.New()

	e.Use(middleware.CORSWithConfig(middleware.CORSConfig{
		AllowOrigins: []string{"*"},
		AllowMethods: []string{http.MethodGet, http.MethodHead, http.MethodPut, http.MethodPatch, http.MethodPost, http.MethodDelete},
	}))

	db, _ := gorm.Open(sqlite.Open("gorm.db"), &gorm.Config{})
	err := db.AutoMigrate(&Product{}, &Cart{}, &Category{})

	if err != nil {
		return
	}

	categories := []Category{
		{Name: "Electronics"},
		{Name: "Clothing"},
		{Name: "Books"},
	}

	for _, c := range categories {
		db.Create(&c)
	}

	// Insert products
	products := []Product{
		{Name: "iPhone X", Price: 999, CategoryRefer: 1},
		{Name: "Samsung Galaxy S10", Price: 899, CategoryRefer: 1},
		{Name: "T-shirt", Price: 19, CategoryRefer: 2},
		{Name: "Jeans", Price: 49, CategoryRefer: 2},
		{Name: "The Catcher in the Rye", Price: 9, CategoryRefer: 3},
		{Name: "To Kill a Mockingbird", Price: 12, CategoryRefer: 3},
	}

	for _, c := range products {
		db.Create(&c)
	}

	registerProductController(e, db)

	registerCartController(e, db)

	registerPaymentController(e)

	e.Logger.Fatal(e.Start(":1323"))
}
