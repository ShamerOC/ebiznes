package main

import "gorm.io/gorm"

type Product struct {
	gorm.Model
	Name          string `json:"name" xml:"name" form:"name" query:"name"`
	CategoryRefer int
	Price         int `json:"price" xml:"price" form:"price" query:"price"`
}
