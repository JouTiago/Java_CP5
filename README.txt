Código de insert utilizado para gerar as tabelas para o banco de dados

CREATE TABLE T_CLIENTE (
    c_cpf VARCHAR2(11) PRIMARY KEY,
    c_email VARCHAR2(255) NOT NULL, 
    c_senha VARCHAR2(255) NOT NULL 
);

CREATE TABLE T_VEICULO (
    v_placa VARCHAR2(7) PRIMARY KEY,     
    v_cnh_registro VARCHAR2(11) NOT NULL, 
    v_cnh_data DATE NOT NULL,             
    v_fabricante VARCHAR2(100) NOT NULL, 
    v_modelo VARCHAR2(100) NOT NULL,    
    v_ano NUMBER(4) NOT NULL,          
    c_cpf VARCHAR2(11),             
    CONSTRAINT fk_cliente_veiculo FOREIGN KEY (c_cpf) REFERENCES T_CLIENTE (c_cpf)
);

CREATE TABLE T_SEGURO_AUTO (
    id NUMBER PRIMARY KEY,                
    c_cpf VARCHAR2(11),                 
    v_placa VARCHAR2(7),            
    premio NUMBER(10, 2) NOT NULL,     
    data_inicio DATE NOT NULL,      
    data_fim DATE NOT NULL,          
    sinistro_grave VARCHAR2(5),       
    CONSTRAINT fk_cliente_seguros FOREIGN KEY (c_cpf) REFERENCES T_CLIENTE (c_cpf),
    CONSTRAINT fk_veiculo_seguros FOREIGN KEY (v_placa) REFERENCES T_VEICULO (v_placa) 
);

CREATE TABLE T_SINISTRO (
    id NUMBER PRIMARY KEY,               
    c_cpf VARCHAR2(11),                  
    v_placa VARCHAR2(7),                  
    data_sinistro DATE NOT NULL,         
    gravidade VARCHAR2(20) NOT NULL,     
    descricao VARCHAR2(255),             
    CONSTRAINT fk_cliente_sinistro FOREIGN KEY (c_cpf) REFERENCES T_CLIENTE (c_cpf),
    CONSTRAINT fk_veiculo_sinistro FOREIGN KEY (v_placa) REFERENCES T_VEICULO (v_placa) 
);