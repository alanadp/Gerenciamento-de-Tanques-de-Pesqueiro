-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 12/11/2026 às 01:48
-- Versão do servidor: 10.4.32-MariaDB
-- Versão do PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `pesqueiro`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `especie`
--

CREATE TABLE `especie` (
  `id` int(11) NOT NULL,
  `nome` varchar(30) NOT NULL,
  `precoKg` decimal(10,2) NOT NULL,
  `descricao` varchar(60) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `especie`
--

INSERT INTO `especie` (`id`, `nome`, `precoKg`, `descricao`) VALUES
(1, 'Linguado', 10.00, 'oii'),
(2, 'Lucio', 20.00, 'eita'),


-- --------------------------------------------------------

--
-- Estrutura para tabela `peixetanque`
--

CREATE TABLE `peixetanque` (
  `idTanque` int(11) NOT NULL,
  `idEspecie` int(11) NOT NULL,
  `quantidade` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `peixetanque`
--

INSERT INTO `peixetanque` (`idTanque`, `idEspecie`, `quantidade`) VALUES
(1, 1, 6),
(2, 3, 20);

-- --------------------------------------------------------

--
-- Estrutura para tabela `produto`
--

CREATE TABLE `produto` (
  `id` int(11) NOT NULL,
  `nome` varchar(30) NOT NULL,
  `preco` decimal(10,2) NOT NULL,
  `descricao` varchar(60) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `produto`
--

INSERT INTO `produto` (`id`, `nome`, `preco`, `descricao`) VALUES
(2, 'vara', 20.00, 'audqsuod'),


-- --------------------------------------------------------

--
-- Estrutura para tabela `tanque`
--

CREATE TABLE `tanque` (
  `id` int(11) NOT NULL,
  `nome` varchar(30) NOT NULL,
  `capacidade` int(11) NOT NULL,
  `ativo` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `tanque`
--

INSERT INTO `tanque` (`id`, `nome`, `capacidade`, `ativo`) VALUES
(1, 'Tanque 1 ', 20, 1),


--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `especie`
--
ALTER TABLE `especie`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `peixetanque`
--
ALTER TABLE `peixetanque`
  ADD PRIMARY KEY (`idTanque`,`idEspecie`),
  ADD KEY `idEspecie` (`idEspecie`);

--
-- Índices de tabela `produto`
--
ALTER TABLE `produto`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `tanque`
--
ALTER TABLE `tanque`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `especie`
--
ALTER TABLE `especie`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `produto`
--
ALTER TABLE `produto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `tanque`
--
ALTER TABLE `tanque`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `peixetanque`
--
ALTER TABLE `peixetanque`
  ADD CONSTRAINT `peixetanque_ibfk_1` FOREIGN KEY (`idTanque`) REFERENCES `tanque` (`id`),
  ADD CONSTRAINT `peixetanque_ibfk_2` FOREIGN KEY (`idEspecie`) REFERENCES `especie` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
