Feature: Generar Template HTML

  Scenario: Se requiere generar template html
    Given Soy usuario autenticado con username "jbodoq" y password "admin123"
    When Genero el template "test" con parametro "nombre" y valor "Test"
    Then Valido que el codigo http status sea "200"
    And Valido que se haya generado el reporte