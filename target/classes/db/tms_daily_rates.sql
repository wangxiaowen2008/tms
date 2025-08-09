DROP TABLE IF EXISTS tms_daily_rates;
CREATE TABLE tms_daily_rates (
    from_currency varchar(25) DEFAULT '',
    to_currency varchar(25) DEFAULT '',
    conversion_date date DEFAULT null,
    conversion_type varchar(40) DEFAULT '',
    conversion_rate varchar(40) DEFAULT '',
    status_code varchar(2) DEFAULT '',
    creation_date date DEFAULT null,
    created_by varchar(25) DEFAULT '',
    last_update_date date DEFAULT null,
    last_updated_by varchar(25) DEFAULT '',
    last_update_login varchar(25) DEFAULT '',
    context varchar(256) DEFAULT '',
    attribute1 varchar(256) DEFAULT '',
    attribute2 varchar(256) DEFAULT '',
    attribute3 varchar(256) DEFAULT '',
    attribute4 varchar(256) DEFAULT '',
    attribute5 varchar(256) DEFAULT '',
    attribute6 varchar(256) DEFAULT '',
    attribute7 varchar(256) DEFAULT '',
    attribute8 varchar(256) DEFAULT '',
    attribute9 varchar(256) DEFAULT '',
    attribute10 varchar(256) DEFAULT '',
    attribute11 varchar(256) DEFAULT '',
    attribute12 varchar(256) DEFAULT '',
    attribute13 varchar(256) DEFAULT '',
    attribute14 varchar(256) DEFAULT '',
    attribute15 varchar(256) DEFAULT '',
    rate_source_code varchar(25) DEFAULT ''
);
ALTER TABLE tms_daily_rates
    ADD CONSTRAINT uc_conversion UNIQUE (from_currency, to_currency, conversion_date, conversion_type);
COMMENT ON TABLE tms_daily_rates IS '每日汇率表';