-- ============================================================
-- V2: Datos semilla (demo) de inventario (farmacia)
-- categoria: ANALGESICO/ANTIBIOTICO/ANTIINFLAMATORIO/ANTIHISTAMINICO/CARDIOVASCULAR/OTROS
-- estado: ACTIVO/AGOTADO/CADUCADO/DISCONTINUADO/EN_ESPERA | moneda: PEN/USD
-- ============================================================

INSERT INTO inventario (codigo_producto, nombre, categoria, presentacion, concentracion,
                        laboratorio, cantidad_disponible, stock_minimo, stock_maximo,
                        precio_unitario, costo_unitario, iva, moneda, requiere_receta,
                        estado, lote, fecha_vencimiento, fecha_fabricacion, ubicacion,
                        creado_en, actualizado_en, creado_por) VALUES
('MED-0001', 'Paracetamol 500mg', 'ANALGESICO', 'Tableta', '500mg', 'Genfar',
 500, 50, 1000, 0.50, 0.20, 18.00, 'PEN', false, 'ACTIVO', 'L-2401',
 CURRENT_DATE + 400, CURRENT_DATE - 120, 'Estante A1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'seed'),

('MED-0002', 'Amoxicilina 500mg', 'ANTIBIOTICO', 'Capsula', '500mg', 'Portugal',
 120, 30, 400, 1.20, 0.60, 18.00, 'PEN', true, 'ACTIVO', 'L-2402',
 CURRENT_DATE + 300, CURRENT_DATE - 90, 'Estante B2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'seed'),

('MED-0003', 'Ibuprofeno 400mg', 'ANTIINFLAMATORIO', 'Tableta', '400mg', 'Genfar',
 15, 40, 500, 0.80, 0.35, 18.00, 'PEN', false, 'ACTIVO', 'L-2403',
 CURRENT_DATE + 200, CURRENT_DATE - 60, 'Estante A2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'seed'),

('MED-0004', 'Loratadina 10mg', 'ANTIHISTAMINICO', 'Tableta', '10mg', 'Medifarma',
 200, 20, 400, 0.60, 0.25, 18.00, 'PEN', false, 'ACTIVO', 'L-2404',
 CURRENT_DATE + 500, CURRENT_DATE - 30, 'Estante C1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'seed'),

('MED-0005', 'Enalapril 10mg', 'CARDIOVASCULAR', 'Tableta', '10mg', 'Farmindustria',
 80, 25, 300, 1.00, 0.45, 18.00, 'PEN', true, 'ACTIVO', 'L-2405',
 CURRENT_DATE + 25, CURRENT_DATE - 200, 'Estante B1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'seed'),

('INS-0001', 'Alcohol en gel 250ml', 'OTROS', 'Frasco', NULL, 'CleanCare',
 300, 50, 600, 5.00, 2.50, 18.00, 'PEN', false, 'ACTIVO', 'L-2406',
 CURRENT_DATE + 15, CURRENT_DATE - 40, 'Almacen', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'seed');

-- Un par de movimientos de ejemplo (producto_id 1 = Paracetamol)
INSERT INTO movimientos_inventario (producto_id, tipo_movimiento, cantidad, motivo,
                                    fecha_movimiento, usuario) VALUES
(1, 'ENTRADA', 500, 'Compra inicial', CURRENT_TIMESTAMP - INTERVAL '5 day', 'seed'),
(1, 'SALIDA', 20, 'Despacho a farmacia', CURRENT_TIMESTAMP - INTERVAL '1 day', 'seed');
