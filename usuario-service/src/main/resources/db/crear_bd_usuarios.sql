-- ============================================================
-- Creacion de la base de datos del microservicio de usuarios
-- SIHCE - Sistema de Historia Clinica Electronica
-- ============================================================
-- IMPORTANTE:
--   Este script SOLO crea la base de datos (y un rol dedicado).
--   Las TABLAS las crea Flyway automaticamente al arrancar
--   usuario-service (ver db/migration/V1__crear_tabla_usuarios.sql).
--
-- Ejecucion (conectado como superusuario 'postgres'):
--   psql -U postgres -f crear_bd_usuarios.sql
--
-- Nota: CREATE DATABASE no puede ir dentro de una transaccion ni
-- ejecutarse condicionalmente en SQL estandar, por eso se usa \gexec.
-- ============================================================

-- ----- 1) Rol/usuario de la aplicacion (opcional pero recomendado) -----
-- Si prefieres usar el usuario 'postgres' por defecto, omite este bloque
-- y ajusta DB_USER / DB_PASSWORD en las variables de entorno.
DO
$$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'clinica_app') THEN
        CREATE ROLE clinica_app WITH LOGIN PASSWORD 'clinica_app_pwd';
    END IF;
END
$$;

-- ----- 2) Crear la base de datos si no existe -----
SELECT 'CREATE DATABASE clinica_usuarios OWNER clinica_app ENCODING ''UTF8'''
WHERE NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'clinica_usuarios')
\gexec

-- ----- 3) Privilegios sobre la base de datos -----
GRANT ALL PRIVILEGES ON DATABASE clinica_usuarios TO clinica_app;
