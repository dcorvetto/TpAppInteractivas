USE master
GO

CREATE DATABASE [TPO_GrupoIV]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'TPO_GrupoIV', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL11.MSSQLSERVER\MSSQL\DATA\TPO_GrupoIV.mdf' , SIZE = 3072KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'TPO_GrupoIV_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL11.MSSQLSERVER\MSSQL\DATA\TPO_GrupoIV_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO

ALTER DATABASE [TPO_GrupoIV] SET COMPATIBILITY_LEVEL = 110
GO

IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [TPO_GrupoIV].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO

ALTER DATABASE [TPO_GrupoIV] SET ANSI_NULL_DEFAULT OFF 
GO

ALTER DATABASE [TPO_GrupoIV] SET ANSI_NULLS OFF 
GO

ALTER DATABASE [TPO_GrupoIV] SET ANSI_PADDING OFF 
GO

ALTER DATABASE [TPO_GrupoIV] SET ANSI_WARNINGS OFF 
GO

ALTER DATABASE [TPO_GrupoIV] SET ARITHABORT OFF 
GO

ALTER DATABASE [TPO_GrupoIV] SET AUTO_CLOSE OFF 
GO

ALTER DATABASE [TPO_GrupoIV] SET AUTO_CREATE_STATISTICS ON 
GO

ALTER DATABASE [TPO_GrupoIV] SET AUTO_SHRINK OFF 
GO

ALTER DATABASE [TPO_GrupoIV] SET AUTO_UPDATE_STATISTICS ON 
GO

ALTER DATABASE [TPO_GrupoIV] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO

ALTER DATABASE [TPO_GrupoIV] SET CURSOR_DEFAULT  GLOBAL 
GO

ALTER DATABASE [TPO_GrupoIV] SET CONCAT_NULL_YIELDS_NULL OFF 
GO

ALTER DATABASE [TPO_GrupoIV] SET NUMERIC_ROUNDABORT OFF 
GO

ALTER DATABASE [TPO_GrupoIV] SET QUOTED_IDENTIFIER OFF 
GO

ALTER DATABASE [TPO_GrupoIV] SET RECURSIVE_TRIGGERS OFF 
GO

ALTER DATABASE [TPO_GrupoIV] SET  DISABLE_BROKER 
GO

ALTER DATABASE [TPO_GrupoIV] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO

ALTER DATABASE [TPO_GrupoIV] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO

ALTER DATABASE [TPO_GrupoIV] SET TRUSTWORTHY OFF 
GO

ALTER DATABASE [TPO_GrupoIV] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO

ALTER DATABASE [TPO_GrupoIV] SET PARAMETERIZATION SIMPLE 
GO

ALTER DATABASE [TPO_GrupoIV] SET READ_COMMITTED_SNAPSHOT OFF 
GO

ALTER DATABASE [TPO_GrupoIV] SET HONOR_BROKER_PRIORITY OFF 
GO

ALTER DATABASE [TPO_GrupoIV] SET RECOVERY FULL 
GO

ALTER DATABASE [TPO_GrupoIV] SET  MULTI_USER 
GO

ALTER DATABASE [TPO_GrupoIV] SET PAGE_VERIFY CHECKSUM  
GO

ALTER DATABASE [TPO_GrupoIV] SET DB_CHAINING OFF 
GO

ALTER DATABASE [TPO_GrupoIV] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO

ALTER DATABASE [TPO_GrupoIV] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO

ALTER DATABASE [TPO_GrupoIV] SET  READ_WRITE 
GO




USE [TPO_GrupoIV]
GO
/****** Object:  Table [dbo].[Cliente]    Script Date: 10/4/2016 5:12:59 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Cliente](
	[codigo_cliente] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](45) NULL,
	[dni] [varchar](45) NULL,
	[domicilio] [varchar](45) NULL,
	[mail] [varchar](45) NULL,
	[telefono] [varchar](45) NULL,
PRIMARY KEY CLUSTERED 
(
	[codigo_cliente] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[EventoReclamo]    Script Date: 10/4/2016 5:13:00 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[EventoReclamo](
	[idEventoReclamo] [int] IDENTITY(1,1) NOT NULL,
	[idReclamo] [int] NULL,
	[estado] [varchar](100) NULL,
	[fecha] [datetime] NULL,
	[detalle] [varchar](250) NULL,
 CONSTRAINT [PK_EventoReclamo] PRIMARY KEY CLUSTERED 
(
	[idEventoReclamo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Factura]    Script Date: 10/4/2016 5:13:00 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Factura](
	[idFactura] [int] IDENTITY(1,1) NOT NULL,
	[fecha] [date] NULL,
	[idCliente] [int] NULL,
 CONSTRAINT [PK_Factura] PRIMARY KEY CLUSTERED 
(
	[idFactura] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[ItemFacturaReclamo]    Script Date: 10/4/2016 5:13:00 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ItemFacturaReclamo](
	[idItemFacturaReclamo] [int] IDENTITY(1,1) NOT NULL,
	[idReclamo] [int] NULL,
	[idFactura] [int] NULL,
	[fecha] [date] NULL,
 CONSTRAINT [PK_ItemFacturaReclamo] PRIMARY KEY CLUSTERED 
(
	[idItemFacturaReclamo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[ItemProductoReclamo]    Script Date: 10/4/2016 5:13:00 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ItemProductoReclamo](
	[idItemProductoReclamo] [int] IDENTITY(1,1) NOT NULL,
	[idReclamo] [int] NULL,
	[codigoProducto] [int] NULL,
	[cantidad] [int] NULL,
 CONSTRAINT [PK_ItemProductoReclamo] PRIMARY KEY CLUSTERED 
(
	[idItemProductoReclamo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[ItemProductoReclamoFaltantes]    Script Date: 10/4/2016 5:13:00 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ItemProductoReclamoFaltantes](
	[idItemProductoReclamoFaltantes] [int] IDENTITY(1,1) NOT NULL,
	[idReclamo] [int] NULL,
	[codigoProducto] [int] NOT NULL,
	[cantidadFaltante] [int] NULL,
	[cantidadFacturada] [int] NULL,
 CONSTRAINT [PK_ItemProductoReclamoFaltantes] PRIMARY KEY CLUSTERED 
(
	[idItemProductoReclamoFaltantes] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Producto]    Script Date: 10/4/2016 5:13:00 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Producto](
	[codigo] [int] IDENTITY(1,1) NOT NULL,
	[titulo] [varchar](255) NOT NULL,
	[descripcion] [varchar](255) NOT NULL,
	[precio] [float] NOT NULL,
	[creador] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[codigo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Reclamo]    Script Date: 10/4/2016 5:13:00 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Reclamo](
	[numero] [int] IDENTITY(1,1) NOT NULL,
	[descripcion] [varchar](255) NOT NULL,
	[cliente] [int] NOT NULL,
	[usuario] [int] NOT NULL,
	[responsable] [int] NOT NULL,
	[solucionado] [bit] NOT NULL,
	[tiempoRespuesta] [float] NULL,
	[zona] [varchar](250) NULL,
	[tipoReclamo] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[numero] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ReclamoCompuesto]    Script Date: 10/4/2016 5:13:00 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ReclamoCompuesto](
	[idReclamoPadre] [int] NOT NULL,
	[idReclamoHijo] [int] NOT NULL,
 CONSTRAINT [PK_ReclamoCompuesto] PRIMARY KEY CLUSTERED 
(
	[idReclamoPadre] ASC,
	[idReclamoHijo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Rol]    Script Date: 10/4/2016 5:13:00 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Rol](
	[idRol] [int] IDENTITY(1,1) NOT NULL,
	[descripcion] [varchar](50) NULL,
 CONSTRAINT [PK_Rol] PRIMARY KEY CLUSTERED 
(
	[idRol] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Usuario]    Script Date: 10/4/2016 5:13:00 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Usuario](
	[codigo] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](45) NULL,
	[apellido] [varchar](45) NULL,
	[usuario] [varchar](45) NULL,
	[clave] [nvarchar](64) NULL,
PRIMARY KEY CLUSTERED 
(
	[codigo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[UsuarioRol]    Script Date: 10/4/2016 5:13:00 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UsuarioRol](
	[idRol] [int] NOT NULL,
	[idUsuario] [int] NOT NULL,
 CONSTRAINT [pk_idEstudio_idEspecialidad] PRIMARY KEY CLUSTERED 
(
	[idRol] ASC,
	[idUsuario] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
ALTER TABLE [dbo].[Reclamo] ADD  DEFAULT ((0)) FOR [solucionado]
GO
ALTER TABLE [dbo].[Reclamo] ADD  DEFAULT ((-1)) FOR [tiempoRespuesta]
GO
ALTER TABLE [dbo].[EventoReclamo]  WITH CHECK ADD  CONSTRAINT [FK_EventoReclamo_Reclamo] FOREIGN KEY([idReclamo])
REFERENCES [dbo].[Reclamo] ([numero])
GO
ALTER TABLE [dbo].[EventoReclamo] CHECK CONSTRAINT [FK_EventoReclamo_Reclamo]
GO
ALTER TABLE [dbo].[Factura]  WITH CHECK ADD  CONSTRAINT [FK_Factura_Cliente] FOREIGN KEY([idCliente])
REFERENCES [dbo].[Cliente] ([codigo_cliente])
GO
ALTER TABLE [dbo].[Factura] CHECK CONSTRAINT [FK_Factura_Cliente]
GO
ALTER TABLE [dbo].[ItemFacturaReclamo]  WITH CHECK ADD  CONSTRAINT [FK_ItemFacturaReclamo_Factura] FOREIGN KEY([idFactura])
REFERENCES [dbo].[Factura] ([idFactura])
GO
ALTER TABLE [dbo].[ItemFacturaReclamo] CHECK CONSTRAINT [FK_ItemFacturaReclamo_Factura]
GO
ALTER TABLE [dbo].[ItemFacturaReclamo]  WITH CHECK ADD  CONSTRAINT [FK_ItemFacturaReclamo_Reclamo] FOREIGN KEY([idReclamo])
REFERENCES [dbo].[Reclamo] ([numero])
GO
ALTER TABLE [dbo].[ItemFacturaReclamo] CHECK CONSTRAINT [FK_ItemFacturaReclamo_Reclamo]
GO
ALTER TABLE [dbo].[ItemProductoReclamo]  WITH CHECK ADD  CONSTRAINT [FK_ItemProductoReclamo_Producto] FOREIGN KEY([codigoProducto])
REFERENCES [dbo].[Producto] ([codigo])
GO
ALTER TABLE [dbo].[ItemProductoReclamo] CHECK CONSTRAINT [FK_ItemProductoReclamo_Producto]
GO
ALTER TABLE [dbo].[ItemProductoReclamo]  WITH CHECK ADD  CONSTRAINT [FK_ItemProductoReclamo_Reclamo] FOREIGN KEY([idReclamo])
REFERENCES [dbo].[Reclamo] ([numero])
GO
ALTER TABLE [dbo].[ItemProductoReclamo] CHECK CONSTRAINT [FK_ItemProductoReclamo_Reclamo]
GO
ALTER TABLE [dbo].[ItemProductoReclamoFaltantes]  WITH CHECK ADD  CONSTRAINT [FK_ItemProductoReclamoFaltantes_Producto] FOREIGN KEY([codigoProducto])
REFERENCES [dbo].[Producto] ([codigo])
GO
ALTER TABLE [dbo].[ItemProductoReclamoFaltantes] CHECK CONSTRAINT [FK_ItemProductoReclamoFaltantes_Producto]
GO
ALTER TABLE [dbo].[ItemProductoReclamoFaltantes]  WITH CHECK ADD  CONSTRAINT [FK_ItemProductoReclamoFaltantes_Reclamo] FOREIGN KEY([idReclamo])
REFERENCES [dbo].[Reclamo] ([numero])
GO
ALTER TABLE [dbo].[ItemProductoReclamoFaltantes] CHECK CONSTRAINT [FK_ItemProductoReclamoFaltantes_Reclamo]
GO
ALTER TABLE [dbo].[Producto]  WITH CHECK ADD  CONSTRAINT [FK_creador] FOREIGN KEY([creador])
REFERENCES [dbo].[Usuario] ([codigo])
GO
ALTER TABLE [dbo].[Producto] CHECK CONSTRAINT [FK_creador]
GO
ALTER TABLE [dbo].[Reclamo]  WITH CHECK ADD  CONSTRAINT [FK_cliente] FOREIGN KEY([cliente])
REFERENCES [dbo].[Cliente] ([codigo_cliente])
GO
ALTER TABLE [dbo].[Reclamo] CHECK CONSTRAINT [FK_cliente]
GO
ALTER TABLE [dbo].[Reclamo]  WITH CHECK ADD  CONSTRAINT [FK_responsable] FOREIGN KEY([responsable])
REFERENCES [dbo].[Usuario] ([codigo])
GO
ALTER TABLE [dbo].[Reclamo] CHECK CONSTRAINT [FK_responsable]
GO
ALTER TABLE [dbo].[Reclamo]  WITH CHECK ADD  CONSTRAINT [FK_usuario] FOREIGN KEY([usuario])
REFERENCES [dbo].[Usuario] ([codigo])
GO
ALTER TABLE [dbo].[Reclamo] CHECK CONSTRAINT [FK_usuario]
GO
ALTER TABLE [dbo].[ReclamoCompuesto]  WITH CHECK ADD  CONSTRAINT [FK_ReclamoCompuesto_Reclamo] FOREIGN KEY([idReclamoPadre])
REFERENCES [dbo].[Reclamo] ([numero])
GO
ALTER TABLE [dbo].[ReclamoCompuesto] CHECK CONSTRAINT [FK_ReclamoCompuesto_Reclamo]
GO
ALTER TABLE [dbo].[ReclamoCompuesto]  WITH CHECK ADD  CONSTRAINT [FK_ReclamoCompuesto_Reclamo1] FOREIGN KEY([idReclamoHijo])
REFERENCES [dbo].[Reclamo] ([numero])
GO
ALTER TABLE [dbo].[ReclamoCompuesto] CHECK CONSTRAINT [FK_ReclamoCompuesto_Reclamo1]
GO
ALTER TABLE [dbo].[UsuarioRol]  WITH CHECK ADD  CONSTRAINT [FK_UsuarioRol_Rol] FOREIGN KEY([idRol])
REFERENCES [dbo].[Rol] ([idRol])
GO
ALTER TABLE [dbo].[UsuarioRol] CHECK CONSTRAINT [FK_UsuarioRol_Rol]
GO
ALTER TABLE [dbo].[UsuarioRol]  WITH CHECK ADD  CONSTRAINT [FK_UsuarioRol_Usuario] FOREIGN KEY([idUsuario])
REFERENCES [dbo].[Usuario] ([codigo])
GO
ALTER TABLE [dbo].[UsuarioRol] CHECK CONSTRAINT [FK_UsuarioRol_Usuario]
GO


USE [TPO_GrupoIV]
GO

/*Inserts clientes*/

INSERT INTO Cliente  values ('Carlos Vives','31000000','Charcas 123','cvives@gmail.com','123456');
INSERT INTO Cliente values ('Sonia Vives','32000000','Charcas 321','svives@gmail.com','654321');
INSERT INTO Cliente values ('Pedro Vives','33000000','Charcas 111','pvives@gmail.com','888888');
INSERT INTO Cliente values ('Juan Vives','34000000','Charcas 222','jvives@gmail.com','777777');
INSERT INTO Cliente values ('Rigoberto Vives','35000000','Charcas 333','rvives@gmail.com','333224');

GO

/*Rol*/

INSERT INTO [dbo].[Rol] VALUES('Distribucion')
INSERT INTO [dbo].[Rol] VALUES ('CallCenter')
INSERT INTO [dbo].[Rol] VALUES ('Administrador')
INSERT INTO [dbo].[Rol] VALUES ('Facturacion')
INSERT INTO [dbo].[Rol] VALUES ('ZonaEntrega')
INSERT INTO [dbo].[Rol] VALUES ('Consulta')

/*Inserts *Usuario*/
INSERT INTO Usuario values ('Estefania','Tesla','etesla','admin');
INSERT INTO Usuario values ('Maria','Tesla','mtesla','admin');
INSERT INTO Usuario values ('Dana','Tesla','dtesla','admin');
INSERT INTO Usuario values ('Susana','Tesla','stesla','admin');
INSERT INTO Usuario values ('Luisa','Tesla','ltesla','admin');

/*Inserts UsuarioRol*/
INSERT INTO [dbo].[UsuarioRol] ([idRol],[idUsuario]) VALUES (1,1)
INSERT INTO [dbo].[UsuarioRol] ([idRol],[idUsuario]) VALUES (2,1)
INSERT INTO [dbo].[UsuarioRol] ([idRol],[idUsuario]) VALUES (3,2)
INSERT INTO [dbo].[UsuarioRol] ([idRol],[idUsuario]) VALUES (4,3)
INSERT INTO [dbo].[UsuarioRol] ([idRol],[idUsuario]) VALUES (5,4)
INSERT INTO [dbo].[UsuarioRol] ([idRol],[idUsuario]) VALUES (6,5)
INSERT INTO [dbo].[UsuarioRol] ([idRol],[idUsuario]) VALUES (6,1)
INSERT INTO [dbo].[UsuarioRol] ([idRol],[idUsuario]) VALUES (6,2)


/*Inserts Productos*/

INSERT INTO Producto values ('Copas Finas','Finas Copas Chinas',100.99,1);
INSERT INTO Producto values ('Vasos Finos','Finos Vasoss Chinos',99.99,2);
INSERT INTO Producto values ('Parlantes Logitech','Suenan Fuerte',500.50,3);
INSERT INTO Producto values ('Placa Arduino UNO','Aprende mucho de Electronica ya mismo!',250.99,4);
INSERT INTO Producto values ('Set de Cuchillas','Afiladisimas',399.99,4);

GO

/*Inserts Factura*/

INSERT INTO [dbo].[Factura]  VALUES ('2016-09-26',5)
GO


/*Inserts Reclamos*/

INSERT INTO Reclamo values ('Producto roto',1,1,1,0,-1,null,'Producto');
INSERT INTO Reclamo values ('Producto que me falta',2,1,1,0,-1, null, 'Faltantes');
INSERT INTO Reclamo values ('Quiero mas productos',3,2,1,0,-1, null, 'Cantidad');
INSERT INTO Reclamo values ('Encontre un mejor precio',4,3,1,0,-1,'zona A','Zona');
INSERT INTO Reclamo values ('No me llego nada che!',5,4,1,0,-1,null,'Faltantes');
INSERT INTO Reclamo values ('Tengo problemas con las facturas',5,4,1,0,-1,null,'Facturacion');

/*Inserts EventoReclamos*/
INSERT INTO [dbo].[EventoReclamo]  VALUES (1,'Solucionado',GETDATE(),'')
GO

