class List {
    List(type) {
        this.type = type;
        this.list = list(type);
    }

    direct(object) {
        // This is used when you directly create a list through the API, and need to convert
        // it to a useable list (with the features a list item has...)
        this.list = object;
    }

    add(object) {
        // This is used when adding an object to the list...
        listAdd(this.list, object);
    }

    get(position) {
        // This is used to grab an object from the list...
        return getItem(this.list, position);
    }

    reset() {
        // This is used for those times when you cant be bothered deleting each item of the list...
        this.list = list(this.type);
    }

    show() {
        // For when you want to show your list...
        say this.list;
    }

    trimEach() {
        // This is used to remove whitespace from a string...
        let length = objSize(this.list);
        say check(length);
        for(let i = 0; i < length; i = i + 1) {
            let str = this.get(i);
            str = trim(str);
            this.replace(i , str);
        }
    }

    replace(position, value) {
        // Replaces the given item with the new value...
        this.list = listReplace(this.list, position, value);
    }

    delete(position) {
        // This is used to remove a selected item from the list...
        this.list = listDel(this.list, position);
    }
}