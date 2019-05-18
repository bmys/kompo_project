create table events
(
  ID varchar(36) not null
    primary key,
  TITLE text null,
  date datetime null,
  description text null,
  locations text null
)
  engine=InnoDB
;

create table reminders
(
  reminderID int auto_increment
    primary key,
  ID text not null,
  title text not null,
  date datetime not null,
  event varchar(36) not null,
  constraint reminders_events_ID_fk
  foreign key (event) references events (ID)
)
  engine=InnoDB
;

create index reminders_events_ID_fk
  on reminders (event)
;

