
CREATE TABLE candidates (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  gender CHAR(1) NOT NULL,
  expected_salary DECIMAL(10,2) NOT NULL,
  additional_info TEXT,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


INSERT INTO candidates (name, email, gender, expected_salary, additional_info) VALUES
  ('Juan Pérez', 'juan.perez@email.com', 'M', 50000.00, 'Desarrollador web con 3 años de experiencia en JavaScript, HTML y CSS. Busca un puesto desafiante en una empresa innovadora.'),
  ('Ana García', 'ana.garcia@email.com', 'F', 60000.00, 'Ingeniera de software con experiencia en Java y Spring Boot. Apasionada por la creación de aplicaciones escalables y eficientes.'),
  ('Carlos López', 'carlos.lopez@email.com', 'M', 45000.00, 'Desarrollador móvil con experiencia en Android y Kotlin. Busca una oportunidad para trabajar en proyectos móviles de alto impacto.'),
  ('María González', 'maria.gonzalez@email.com', 'F', 75000.00, 'Experta en análisis de datos con dominio de Python y R. Busca un puesto que combine análisis y desarrollo de software.'),
  ('Pedro Sánchez', 'pedro.sanchez@email.com', 'M', 58000.00, 'Desarrollador full-stack con experiencia en JavaScript, Node.js y React. Busca un puesto que le permita trabajar en el front-end y el back-end.'),
;


