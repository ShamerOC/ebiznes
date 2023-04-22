package main

import (
	"github.com/labstack/echo/v4"
	"gorm.io/gorm"
	"net/http"
	"strconv"
)

func registerProductController(e *echo.Echo, db *gorm.DB) {
	var product []Product

	e.GET("/products", func(c echo.Context) error {
		// Find all products in the database
		var products []Product
		if err := db.Find(&products).Error; err != nil {
			return c.String(http.StatusInternalServerError, "error retrieving products")
		}

		// Return the products as JSON
		return c.JSON(http.StatusOK, products)
	})

	// Get Product By ID
	e.GET("/products/:id", func(c echo.Context) error {
		id, err := strconv.Atoi(c.Param("id"))
		if err != nil {
			return c.String(http.StatusBadRequest, "invalid product ID")
		}

		// Find the product with the given ID
		var product Product
		if err := db.First(&product, id).Error; err != nil {
			return c.String(http.StatusNotFound, "product not found")
		}

		// Return the product as JSON
		return c.JSON(http.StatusOK, product)
	})

	// Create New Product
	e.POST("/products", func(c echo.Context) error {
		p := new(Product)
		if err := c.Bind(p); err != nil {
			return err
		}
		db.Create(&p)
		println(p.Name)

		return c.JSON(http.StatusCreated, p)
	})

	// Update Product By ID
	e.PUT("/products/:id", func(c echo.Context) error {
		id, _ := strconv.Atoi(c.Param("id"))
		p := new(Product)
		if err := c.Bind(p); err != nil {
			return err
		}
		db.First(&product, id)
		db.Model(&product).Updates(p)

		println(p.Name)

		return c.JSON(http.StatusOK, p)
	})

	// Delete Product By ID
	e.DELETE("/products/:id", func(c echo.Context) error {
		id, _ := strconv.Atoi(c.Param("id"))
		db.Delete(&product, id)

		return c.NoContent(http.StatusNoContent)
	})

}
