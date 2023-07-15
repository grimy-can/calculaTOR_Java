enum RomeDigit {

    I(1),
    II(2),
    III(3),
    IV(4),
    V(5),
    VI(6),
    VII(7),
    VIII(8),
    IX(9),
    X(10),
    L(50),
    C(100),
    XC(90);

    final Integer arabDigit;

    RomeDigit(Integer arabDigit) {
        this.arabDigit = arabDigit;
    }
}

