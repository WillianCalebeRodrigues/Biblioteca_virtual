-- ============================================
-- INSERIR AUTORES (expandido para 15 autores)
-- ============================================
INSERT INTO autores (nome, email, datanascimento, biografia) VALUES 
-- Autores existentes
('José de Alencar', 'alencar@literatura.com', '1829-05-01', 'Escritor brasileiro, representante do Romantismo'),
('Machado de Assis', 'machado@academia.com', '1839-06-21', 'Fundador da Academia Brasileira de Letras'),
('Clarice Lispector', 'clarice@contos.com', '1920-12-10', 'Uma das mais importantes escritoras do século XX'),

-- Novos autores
('Cecília Meireles', 'cecilia@poesia.com', '1901-11-07', 'Poetisa, pintora, professora e jornalista brasileira'),
('Carlos Drummond de Andrade', 'drummond@poesia.com', '1902-10-31', 'Considerado um dos maiores poetas brasileiros do século XX'),
('Jorge Amado', 'jorge@bahia.com', '1912-08-10', 'Escritor baiano, autor de romances regionalistas'),
('Graciliano Ramos', 'graciliano@literatura.com', '1892-10-27', 'Romancista, cronista e contista do Modernismo'),
('Érico Veríssimo', 'erico@sul.com', '1905-12-17', 'Escritor gaúcho, autor de importantes romances históricos'),
('Rachel de Queiroz', 'rachel@nordeste.com', '1910-11-17', 'Primeira mulher a entrar para a Academia Brasileira de Letras'),
('Monteiro Lobato', 'monteiro@sitio.com', '1882-04-18', 'Criador do Sítio do Picapau Amarelo, precursor da literatura infantil'),
('Lima Barreto', 'lima@suburbio.com', '1881-05-13', 'Escritor pré-modernista, cronista da vida urbana carioca'),
('Mário de Andrade', 'mario@modernismo.com', '1893-10-09', 'Poeta, romancista e musicólogo, líder do Modernismo brasileiro'),
('Guimarães Rosa', 'guimaraes@sertao.com', '1908-06-27', 'Escritor, diplomata e médico, renovador da literatura brasileira'),
('Manuel Bandeira', 'bandeira@poesia.com', '1886-04-19', 'Poeta, crítico literário e tradutor do Modernismo'),
('Lygia Fagundes Telles', 'lygia@contos.com', '1923-04-19', 'Escritora paulista, mestra dos contos psicológicos');

-- ============================================
-- INSERIR LIVROS (expandido para 45 livros)
-- ============================================
INSERT INTO livros (titulo, isbn, datapublicacao, numeropaginas, disponivel, autor_id) VALUES 
-- Livros existentes
('O Guarani', '123-4567890123', '1857-01-01', 320, true, 1),
('Dom Casmurro', '123-4567890125', '1899-01-01', 256, false, 2),
('A Hora da Estrela', '123-4567890127', '1977-01-01', 96, true, 3),

-- Mais livros de José de Alencar (autor_id = 1)
('Iracema', '978-8501058201', '1865-01-01', 144, true, 1),
('Senhora', '978-8508040773', '1875-01-01', 208, true, 1),
('Lucíola', '978-8572326636', '1862-01-01', 176, false, 1),

-- Mais livros de Machado de Assis (autor_id = 2)
('Memórias Póstumas de Brás Cubas', '978-8535911664', '1881-01-01', 368, true, 2),
('Quincas Borba', '978-8520923481', '1891-01-01', 304, true, 2),
('O Alienista', '978-8594318602', '1882-01-01', 88, true, 2),
('Esaú e Jacó', '978-8525406354', '1904-01-01', 272, false, 2),

-- Mais livros de Clarice Lispector (autor_id = 3)
('A Paixão Segundo G.H.', '978-8520925683', '1964-01-01', 176, true, 3),
('Laços de Família', '978-8532511010', '1960-01-01', 168, true, 3),
('Perto do Coração Selvagem', '978-8532526755', '1943-01-01', 200, false, 3),
('Água Viva', '978-8532530363', '1973-01-01', 96, true, 3),

-- Livros de Cecília Meireles (autor_id = 4)
('Viagem', '978-8526021976', '1939-01-01', 128, true, 4),
('Romanceiro da Inconfidência', '978-8526020788', '1953-01-01', 432, true, 4),
('Vaga Música', '978-8526022171', '1942-01-01', 96, true, 4),

-- Livros de Carlos Drummond de Andrade (autor_id = 5)
('Sentimento do Mundo', '978-8535922424', '1940-01-01', 112, true, 5),
('A Rosa do Povo', '978-8535922431', '1945-01-01', 144, false, 5),
('Claro Enigma', '978-8535922448', '1951-01-01', 128, true, 5),

-- Livros de Jorge Amado (autor_id = 6)
('Capitães da Areia', '978-8535929720', '1937-01-01', 280, true, 6),
('Gabriela, Cravo e Canela', '978-8535929737', '1958-01-01', 424, true, 6),
('Dona Flor e Seus Dois Maridos', '978-8535929744', '1966-01-01', 544, false, 6),
('Tieta do Agreste', '978-8535929751', '1977-01-01', 448, true, 6),

-- Livros de Graciliano Ramos (autor_id = 7)
('Vidas Secas', '978-8501017666', '1938-01-01', 176, true, 7),
('São Bernardo', '978-8501017673', '1934-01-01', 192, true, 7),
('Angústia', '978-8501017680', '1936-01-01', 240, false, 7),

-- Livros de Érico Veríssimo (autor_id = 8)
('O Tempo e o Vento - O Continente', '978-8535916607', '1949-01-01', 680, true, 8),
('Olhai os Lírios do Campo', '978-8535916614', '1938-01-01', 304, true, 8),
('Incidente em Antares', '978-8535916621', '1971-01-01', 432, true, 8),

-- Livros de Rachel de Queiroz (autor_id = 9)
('O Quinze', '978-8503012683', '1930-01-01', 160, true, 9),
('Memorial de Maria Moura', '978-8503012690', '1992-01-01', 752, false, 9),
('Dôra, Doralina', '978-8503012706', '1975-01-01', 320, true, 9),

-- Livros de Monteiro Lobato (autor_id = 10)
('Reinações de Narizinho', '978-8525054685', '1931-01-01', 296, true, 10),
('O Sítio do Picapau Amarelo', '978-8525054692', '1920-01-01', 144, true, 10),
('Urupês', '978-8525054708', '1918-01-01', 208, true, 10),

-- Livros de Lima Barreto (autor_id = 11)
('Triste Fim de Policarpo Quaresma', '978-8582850831', '1915-01-01', 272, true, 11),
('Clara dos Anjos', '978-8582850848', '1948-01-01', 192, false, 11),
('Os Bruzundangas', '978-8582850855', '1922-01-01', 160, true, 11),

-- Livros de Mário de Andrade (autor_id = 12)
('Macunaíma', '978-8503013055', '1928-01-01', 216, true, 12),
('Amar, Verbo Intransitivo', '978-8503013062', '1927-01-01', 160, true, 12),

-- Livros de Guimarães Rosa (autor_id = 13)
('Grande Sertão: Veredas', '978-8520923030', '1956-01-01', 624, true, 13),
('Sagarana', '978-8520923047', '1946-01-01', 368, true, 13),
('Primeiras Estórias', '978-8520923054', '1962-01-01', 232, false, 13),

-- Livros de Manuel Bandeira (autor_id = 14)
('Libertinagem', '978-8535926378', '1930-01-01', 96, true, 14),
('Estrela da Vida Inteira', '978-8535926385', '1966-01-01', 448, true, 14),

-- Livros de Lygia Fagundes Telles (autor_id = 15)
('As Meninas', '978-8535931075', '1973-01-01', 336, true, 15),
('Ciranda de Pedra', '978-8535931082', '1954-01-01', 256, false, 15),
('Verão no Aquário', '978-8535931099', '1963-01-01', 176, true, 15);

-- ============================================
-- INSERIR EMPRÉSTIMOS (expandido para 25)
-- ============================================
INSERT INTO emprestimos (nomeusuario, emailusuario, dataemprestimo, datadevolucaoprevista, livro_id) VALUES 
-- Empréstimos existentes
('João Santos', 'joao@email.com', '2024-11-10', '2024-11-24', 2),
('Ana Costa', 'ana@email.com', '2024-11-15', '2024-11-29', 3),

-- Novos empréstimos
('Maria Silva', 'maria.silva@email.com', '2024-10-05', '2024-10-19', 7),
('Pedro Oliveira', 'pedro.oliveira@email.com', '2024-10-20', '2024-11-03', 15),
('Carla Souza', 'carla.souza@email.com', '2024-11-01', '2024-11-15', 22),
('Lucas Pereira', 'lucas.pereira@email.com', '2024-11-08', '2024-11-22', 28),
('Fernanda Lima', 'fernanda.lima@email.com', '2024-11-12', '2024-11-26', 34),
('Ricardo Alves', 'ricardo.alves@email.com', '2024-11-14', '2024-11-28', 5),
('Juliana Rocha', 'juliana.rocha@email.com', '2024-11-16', '2024-11-30', 18),
('Marcos Barbosa', 'marcos.barbosa@email.com', '2024-11-18', '2024-12-02', 25),
('Beatriz Martins', 'beatriz.martins@email.com', '2024-10-25', '2024-11-08', 31),
('Gabriel Santos', 'gabriel.santos@email.com', '2024-11-02', '2024-11-16', 12),
('Amanda Ferreira', 'amanda.ferreira@email.com', '2024-11-05', '2024-11-19', 39),
('Thiago Costa', 'thiago.costa@email.com', '2024-11-09', '2024-11-23', 8),
('Patricia Mendes', 'patricia.mendes@email.com', '2024-11-11', '2024-11-25', 20),
('Rafael Cardoso', 'rafael.cardoso@email.com', '2024-11-13', '2024-11-27', 43),
('Camila Ribeiro', 'camila.ribeiro@email.com', '2024-11-17', '2024-12-01', 16),
('Bruno Araújo', 'bruno.araujo@email.com', '2024-11-19', '2024-12-03', 29),
('Vanessa Dias', 'vanessa.dias@email.com', '2024-10-28', '2024-11-11', 36),
('Felipe Gomes', 'felipe.gomes@email.com', '2024-11-03', '2024-11-17', 10),
('Larissa Pinto', 'larissa.pinto@email.com', '2024-11-06', '2024-11-20', 24),
('Diego Moreira', 'diego.moreira@email.com', '2024-11-07', '2024-11-21', 41),
('Renata Castro', 'renata.castro@email.com', '2024-11-20', '2024-12-04', 19),
('Gustavo Nunes', 'gustavo.nunes@email.com', '2024-11-21', '2024-12-05', 33);