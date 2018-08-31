let shipx = 0;
let shipy = 0;
let ferryx = 0;
let ferryy = 0;

let spaceHVec = 0;
let spaceVVec = 0;

let ferryHVec = 0;
let ferryVVec = 0;

let distance = 0;

let firstRun = true;
let connects = false;
let outOfRange = false;

let attempts = 10;
let pulseUses = 0;

fn genRand() {
    let max = 9;
    let min = -9;
    shipx = randRange(min, max);
    shipy = randRange(min, max);
    ferryx = randRange(min, max);
    ferryy = randRange(min, max);
    spaceHVec = randRange(-3, 3);
    spaceVVec = randRange(-2, 2);
    distance = randRange(1, 15);
}

fn Menu() {

    if(firstRun){
        say "Space x pos = " + shipx + ", y pos = " + shipy;
        say "Ferry x pos = " + ferryx + ", y pos = " + ferryy;
        say "Initial distance is " + distance;
        firstRun = false;
    }

    say "Space vehicle: Horizon vector = " + spaceHVec + ", Vertical vector = " + spaceVVec;
    say "Ferry: Horizontal vector = " + ferryHVec + ", Vertical vector = " + ferryVVec;

    say "     OO Ferry Menu";
    say "Port Up";
    say "Starboard Up";
    say "Starboard Down";
    say "Port Down";
    say "Port";
    say "Starboard";
    say "Vertical Up";
    say "Vertical Down";
    say "Maintain Current";
    say "Ground Beacon Go";

    return ask("     Enter choice >> ");
}

fn updatePos(propx, propy) {
    ferryVVec = ferryVVec + propy;
    ferryHVec = ferryHVec + propx;

    ferryx = ferryx + ferryHVec;
    ferryy = ferryy + ferryVVec;

    shipx = shipx + spaceHVec;
    shipy = shipy + spaceVVec;

    distance = 1.0;

    say "";
    say "Number of attempts left = " + attempts;

    if ( shipx == ferryx and shipy == ferryy) {
        connects = true;
    }
    if (distance > 12) {
        outOfRange = true;
    }

    let attemptsLeft = false;
    if (attempts != 0) {
        attemptsLeft = true;
    }

    if (!connects and !outOfRange and attemptsLeft) {
        say "";
        say "Position of Space Vehicle is x = " + shipx + ", y = " + shipy;
        say "Position of the Ferry is x = " + ferryx + ", y = " + ferryy;
        say "Distance is " + distance;
        for (let i = 0; i < 4; i = i + 1) { say ""; }

    } else if (connects and !outOfRange and attemptsLeft) {
        say "";
        say "Great flying pilot!! Passengers are safely on the Space Vehicle";
        say "Take a break";
    } else if (!connects and outOfRange and attemptsLeft) {
        say "Distance is " + distance;
        for (let i = 0; i < 4; i = i + 1) { say ""; }
        say "Really awkward lost contact with the Space Vehicle";
    }
}

fn handleResult(incomming) {
    let propx = 0;
    let propy = 0;
    let valid = true;
    let askPulse = true;

    if (incomming == "port up") {
        propy = 1;
        propx = -1;
    } else if (incomming == "starboard up") {
        propy = 1;
        propx = 1;
    } else if (incomming == "starboard down") {
        propy = -1;
        propx = 1;
    } else if (incomming == "port down") {
        propy = -1;
        propx = -1;
    } else if (incomming == "port") {
        propx = -2;
    } else if (incomming == "starboard") {
        propx = 2;
    } else if (incomming == "vertical up") {
        propy = 2;
    } else if (incomming == "vertical down") {
        propy = -2;
    } else if (incomming == "maintain current") {
        attempts = attempts + 1;
    } else if (incomming == "ground beacon go") {
        attempts = attempts + 1;
        say "Better to save everyone than try!";
    } else {
        attempts = attempts + 1;
        valid = false;
        askPulse = false;
        say incomming + " is not a valid command. Re-enter command";
    }

    if (valid and askPulse and pulseUses != 5) {
            let res = ask("Pulse command [y/n] ? >> ");
            if (res == "y") {
                propx = propx * 2;
                propy = propy * 2;
                pulseUses = pulseUses + 1;
            }
        }
        attempts = attempts - 1;

        if (valid){
            updatePos(propx, propy);
        } 
}
genRand();


while(attempts != 0 and !connects and !outOfRange) {
    let command = Menu();
    handleResult(command);
}