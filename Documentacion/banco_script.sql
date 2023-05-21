create table account
(
    numero_cuenta  int(10)     not null
        primary key,
    fecha_creacion varchar(10) null,
    saldo          double      not null
);

create table users
(
    nif             varchar(9)   not null
        primary key,
    anyo_nacimiento varchar(4)   null,
    apellidos       varchar(100) null,
    direccion       varchar(100) null,
    email           varchar(100) null,
    nombre          varchar(100) null,
    pass            varchar(200) null,
    telefono        int(9)       null,
    constraint UK_guj665i59w2j9nodmvo8aef0e
        unique (pass)
);

create table transaction
(
    codigo_op      int auto_increment
        primary key,
    cantidad       double       null,
    fecha_op       date         null,
    tipo_op        smallint(10) null,
    cuenta_destino int(10)      null,
    cuenta_origen  int(10)      null,
    usuario        varchar(9)   null,
    constraint FK5ojuw2nu5bb3o45vhup0864yh
        foreign key (usuario) references users (nif),
    constraint FKc7642wssjat17n28be8qmhm88
        foreign key (cuenta_destino) references account (numero_cuenta),
    constraint FKt2j02ruxgh8xsh6k8cgrr2phu
        foreign key (cuenta_origen) references account (numero_cuenta)
);

create table user_account
(
    cuenta_banco int(200)     not null,
    user_nif     varchar(255) not null,
    primary key (cuenta_banco, user_nif),
    constraint FK1mwqtb5wvqi7xiuvuwa4i40nj
        foreign key (user_nif) references users (nif),
    constraint FK4tkjbptx4y9jkoyohfkqkq17c
        foreign key (cuenta_banco) references account (numero_cuenta)
);


