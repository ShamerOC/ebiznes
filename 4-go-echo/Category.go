package main

import "gorm.io/gorm"

type Category struct {
	gorm.Model
	Name string `json:"name" xml:"name" form:"name" query:"name"`
}
