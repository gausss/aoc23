package main

import (
	"gauss/aoc23/utils"
	"testing"
)

func TestComputePolymers(t *testing.T) {
	var lines = utils.ReadFile("resources/Ex1_test.txt")
	var result = computePolymers(lines, map[string]int{"NN": 1, "NC": 1, "CB": 1})
	println(lines)
	if result != 2188189693529 {
		t.Error("Wrong number of possible paths")
	}
}
func TestSolve14(t *testing.T) {
	var lines = utils.ReadFile("resources/Ex1.txt")
	println(lines)
}
