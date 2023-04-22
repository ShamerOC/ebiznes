package main

import "gorm.io/gorm"

type Cart struct {
	gorm.Model
	Id       uint      `json:"id" xml:"id" form:"id" query:"id"`
	Products []Product `json:"products" xml:"products" form:"products" gorm:"many2many:cart_products;"`
}
