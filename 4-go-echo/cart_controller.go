package main

import (
	"github.com/labstack/echo/v4"
	"gorm.io/gorm"
	"net/http"
	"strconv"
)

func registerCartController(e *echo.Echo, db *gorm.DB) {

	// Get All Carts
	e.GET("/carts", func(c echo.Context) error {
		// Find all carts in the database
		var carts []Cart
		if err := db.Find(&carts).Error; err != nil {
			return c.String(http.StatusInternalServerError, "error retrieving carts")
		}
		// Return the carts as JSON
		return c.JSON(http.StatusOK, carts)
	})

	// Get Cart By ID
	e.GET("/carts/:id", func(c echo.Context) error {
		// Find the cart with the given ID
		var cart Cart
		id, _ := strconv.Atoi(c.Param("id"))

		if err := db.First(&cart, id).Error; err != nil {
			return c.String(http.StatusNotFound, "cart not found")
		}
		// Return the cart as JSON
		return c.JSON(http.StatusOK, cart)
	})

	// Create New Cart
	e.POST("/carts", func(c echo.Context) error {
		cart := new(Cart)
		if err := c.Bind(cart); err != nil {
			return err
		}
		db.Create(&cart)
		return c.JSON(http.StatusCreated, cart)
	})

	// Update Cart By ID
	e.PUT("/carts/:id", func(c echo.Context) error {
		id, _ := strconv.Atoi(c.Param("id"))
		cart := new(Cart)
		if err := c.Bind(cart); err != nil {
			return err
		}
		db.First(&cart, id)
		return c.JSON(http.StatusOK, cart)
	})

	// Delete Cart By ID
	e.DELETE("/carts/:id", func(c echo.Context) error {
		id, _ := strconv.Atoi(c.Param("id"))
		db.Delete(&Cart{}, id)
		return c.JSON(http.StatusOK, "Cart Deleted")
	})

	// Add Product To Cart
	e.POST("/carts/:id/products", func(c echo.Context) error {
		id, _ := strconv.Atoi(c.Param("id"))
		product := new(Product)
		if err := c.Bind(product); err != nil {
			return err
		}
		var cart Cart
		db.First(&cart, id)
		cart.Products = append(cart.Products, *product)
		db.Save(&cart)
		return c.JSON(http.StatusOK, cart)
	})
}
