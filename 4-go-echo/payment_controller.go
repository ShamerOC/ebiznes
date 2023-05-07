package main

import (
	"github.com/labstack/echo/v4"
	"net/http"
)

func registerPaymentController(e *echo.Echo) {
	e.POST("/pay", func(c echo.Context) error {
		return c.JSON(http.StatusCreated, "{\"message\": \"Payment created\"}")
	})

}
