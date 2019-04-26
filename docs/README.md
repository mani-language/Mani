# Máni
## What is it?
Máni is an interpreted language that is simple to learn, and easy to use.

The idea behind it is, take some of the good parts of other languages like nodejs, c++, python etc.
Implement them into this, and try and build an "Alpha" language... if that is even a such thing.

## What Máni code looks like.
~~~ mani
# "arrays";

class Vector {
    Vector(a, b, c) {
        this.x = a;
        this.y = b;
        this.z = c;
    }

    get() {
        return [this.x, this.y, this.z];
    }

    add(v) {
        if (v is "list") {
            return Vector(this.x+v.at(0), this.y+v.at(1), this.z+v.at(2));
        } else if (v is "number") {
            return Vector(this.x + v, this.y + v, this.z + v);
        } else if (v is "vector") {
            return Vector(this.x + v.x, this.y + v.y, this.z + v.z);
        }
        return nil;
    }

    show() {
        return "[" + this.x + "," + this.y +"," + this.z + "]";
    }


}

let v1 = Vector(1, 2, 3);
let v2 = Vector(4, 5, 6);
let v3 = v1.add(v2);

say v1 + " + " + v2 + " = " + v3;
~~~

### Syntax highlighting
|   [**ATOM**](https://github.com/crazywolf132/Mani-Atom)   |   [**VSCODE**](https://github.com/crazywolf132/Mani-vscode)   |
|:----------------:|:----------------:|
|   [<img src="https://raw.githubusercontent.com/github/explore/80688e429a7d4ef2fca1e82350fe8e3517d3494d/topics/atom/atom.png" width="80">](https://atom.io)  | [<img src="https://user-images.githubusercontent.com/14907694/30436929-a3594ef6-996d-11e7-91e0-ae34fdc040fb.png" width="80">](https://code.visualstudio.com)    |

## Team
|   [**Brayden Moon**](https://github.com/crazywolf132)  |   [**Joe Rickard**](https://github.com/Kalekdan)   |
|:----------------:|:----------------:|
| [<img src="https://avatars3.githubusercontent.com/u/6337115?s=460&v=4" width="80">](https://github.com/crazywolf132) | [<img src="https://avatars3.githubusercontent.com/u/6087595?s=460&v=4" width="80">](https://github.com/Kalekdan)  |
| Founder | Contributor |
| [<img src="https://github.com/favicon.ico" width="15"> Github](https://github.com/crazywolf132)  |  [<img src="https://github.com/favicon.ico" width="15"> Github](https://github.com/Kalekdan) |
