function capacityToColorCode(c) {
    if (c < 100) {
        return "#56B523";
    } else if (c < 200) {
        return "#F76A08";
    } else {
        return "#40A8C4";
    }
}