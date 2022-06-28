set schema 'APP';

create table constructo_user
(
    id bigint NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1),
    firstName varchar(100),
    lastName varchar(100),
    username varchar(100),
    email varchar(200),
    password varchar(200),
    role varchar(50) not null
);

create table garment (
    id bigint NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1),
    garmentType varchar(50),
    name varchar(200) not null,
    description varchar(1000)
);

create table construction_step (
    id  bigint NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1),
    stepType varchar(40),
    text varchar(300) not null,
    garment_id bigint not null,
    utilities varchar(100),
    sortOrder int
);

alter table construction_step add constraint construction_step_garmentRefFk foreign key (garment_id) references garment;

create table user_result (
    id  bigint NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1),
    rightAmount int,
    answerTime date,
    passed boolean,
    attemptedGarment_id bigint not null,
    user_id bigint not null
);

alter table user_result add constraint user_result_garmentRefFk foreign key (attemptedGarment_id) references garment;
alter table user_result add constraint user_result_userRefFk foreign key (user_id) references constructo_user;


