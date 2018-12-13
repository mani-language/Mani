import("lists");
class File {
    File(name) {
        this.name = name;
        this.loaded = false;
        this.contents = "";
    }

    load() {
        this.contents = getFile(this.name);
        this.loaded = true;
    }

    toList(list) {
        if (this.loaded == false) {
            say "Please load the file first using '.load();'";
        } else {
            let holder = split(this.contents, "\n");
            list.direct(holder);
        }
    }
}