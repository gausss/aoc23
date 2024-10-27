package main

import (
	"strings"
)

var mappings map[string]string

func computePolymers(input []string, template map[string]int) (result int) {
	mappings = make(map[string]string)
	for _, line := range input {
		mapping := strings.Split(line, " -> ")
		mappings[mapping[0]] = mapping[1]
	}
	var polymer = template
	for i := 0; i < 40; i++ {
		polymer = polymerize(polymer)
	}
	min, max := interval(polymer)
	return (max-min)/2 + 1
}
func polymerize(template map[string]int) (polymer map[string]int) {
	polymer = make(map[string]int)
	for token, count := range template {
		if mapping, ok := mappings[token]; ok {
			polymer[token[:1]+mapping] += count
			polymer[mapping+token[1:]] += count
		} else {
			polymer[token] = count
		}
	}
	return
}
func interval(occurences map[string]int) (min int, max int) {
	byCharacter := make(map[byte]int)
	for token, count := range occurences {
		byCharacter[token[0]] += count
		byCharacter[token[1]] += count
	}
	for _, value := range byCharacter {
		if min == 0 || value < min {
			min = value
		}
		if max == 0 || value > max {
			max = value
		}
	}
	return
}
