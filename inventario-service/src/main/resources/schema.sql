CREATE TABLE IF NOT EXISTS inventario (
    id BIGSERIAL PRIMARY KEY,
    codigo_producto VARCHAR(50) UNIQUE NOT NULL,
    nombre VARCHAR(200) NOT NULL,
    descripcion TEXT,
    categoria VARCHAR(50),
    presentacion VARCHAR(100) NOT NULL,
    concentracion VARCHAR(100),
    laboratorio VARCHAR(200),
    requiere_receta BOOLEAN DEFAULT FALSE,
    proveedor_id BIGINT,
    categoria_id BIGINT,
    cantidad_disponible INT NOT NULL CHECK (cantidad_disponible >= 0),
    stock_minimo INT NOT NULL CHECK (stock_minimo >= 0),
    stock_maximo INT,
    ubicacion VARCHAR(100),
    lote VARCHAR(50),
    fecha_vencimiento DATE NOT NULL,
    fecha_fabricacion DATE,
    precio_unitario DECIMAL(10,2) NOT NULL,
    costo_unitario DECIMAL(10,2),
    moneda VARCHAR(3) DEFAULT 'PEN',
    iva DECIMAL(5,2),
    estado VARCHAR(20) DEFAULT 'ACTIVO',
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    creado_por VARCHAR(100),
    actualizado_por VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS movimientos_inventario (
    id BIGSERIAL PRIMARY KEY,
    producto_id BIGINT NOT NULL,
    tipo_movimiento VARCHAR(20) NOT NULL,
    cantidad INT NOT NULL,
    fecha_movimiento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario VARCHAR(100),
    motivo VARCHAR(255),
    observaciones TEXT
);

CREATE INDEX IF NOT EXISTS idx_codigo_producto ON inventario(codigo_producto);
CREATE INDEX IF NOT EXISTS idx_fecha_vencimiento ON inventario(fecha_vencimiento);
CREATE INDEX IF NOT EXISTS idx_estado ON inventario(estado);
CREATE INDEX IF NOT EXISTS idx_producto_id ON movimientos_inventario(producto_id);
