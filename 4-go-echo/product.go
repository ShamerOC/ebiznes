package main

import "gorm.io/gorm"

type Product struct {
	gorm.Model
	Id            uint   `json:"id" xml:"id" form:"id" query:"id"`
	Name          string `json:"name" xml:"name" form:"name" query:"name"`
	CategoryRefer int
	Category      Category `json:"category" xml:"category" form:"category" query:"category" gorm:"foreignKey:CategoryRefer"`
	Price         int      `json:"price" xml:"price" form:"price" query:"price"`
}
