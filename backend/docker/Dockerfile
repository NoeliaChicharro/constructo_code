FROM mcr.microsoft.com/mssql/server:2017-latest-ubuntu

RUN mkdir /docker-entrypoint-initdb.d

COPY bin/*.sh /docker-entrypoint-initdb.d/

VOLUME /docker-entrypoint-initdb.d
EXPOSE 1433


RUN ln -s /opt/mssql-tools/bin/sqlcmd /usr/local/bin/sqlcmd \
    && ln -s /opt/mssql-tools/bin/bcp /usr/local/bin/bcp

COPY bin/*.sh /usr/local/bin/

RUN chmod +x /usr/local/bin/docker-entrypoint.sh && \
    chmod +x /usr/local/bin/docker-entrypoint-initdb.sh

ENTRYPOINT ["docker-entrypoint.sh"]