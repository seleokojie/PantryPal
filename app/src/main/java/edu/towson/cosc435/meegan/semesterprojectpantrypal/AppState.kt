package edu.towson.cosc435.meegan.semesterprojectpantrypal


// AppState is an object used for centralized state management in the program.
// Specifically, it provides a way to store and access a list of Item objects
// that represent the items in an inventory.

// By using AppState to manage the list of items, we can ensure that any changes
// made to the list are consistent across the entire program, and that all parts
// of the program are accessing the same, up-to-date list of items. This can help
// prevent issues with inconsistent or conflicting state that can arise when
// different parts of the program are managing their own separate lists of items.


object AppState {
        var items: List<Item> = listOf()
        var loggedInUserId = -1
    }

