# STD-projeto_do_balao_Java
Projeto 1 - projeto_do_balao da disciplina Sistemas Distribuídos(STD) do curso de Engenharia de Telecomunicações - IFSC-SJ. Feito na linguagem Java e na IDE NetBeans

# Requesitos Projeto Loon
Balão:

*Balao ao ser lançado

- sabe qual o identificador do ultimo balao que fora lançado anteriormente 
- viznho - Ok - esta no arquivo de configuraçao do balao(ex: balao1.txt, balao2.txt), que esta na pasta balao do projeto

- todos os baloes antes de serem laçados conhecem a localização de todas as radio base terrestre ERB - Ok - esta no arquivo ERBs.txt, que esta na pasta balao do projeto

*Balao ao receber uma mensagem

- verifica se esta proximo de uma ERB, se estiver entao encaminha a mensagem para a ERB - Ok

- verifica se esta proximo de uma ERB, se não estiver entao encaminha a mensagem para o seu vizinho - Ok


*o radio de um balao so o possibilita se comunicar com ERBs que estejam a no maximo 40KM(40000m) de distancia. - Ok


*Para efeitos de depuração, deve-se imprimir uma única mensagem depuração no console de cada balão por onde o arquivo passou até atingir a ERB - Ok


*O processo de cada balão ao ser instanciado já saberá de antemão seu identificador único(idUnicoBalao), suas coordenadas geográficas (latitude e longitude) e o idUnicoBalao de seu vizinho, caso esse possua vizinho

iDUnicoBalao deve ser: IP:PORTA

Essas informações poderiam ser fornecidas, por exemplo, por meio de argumentos de linha de comando ou por meio de um arquivo texto; - Ok 

- esta no arquivo de configuraçao do balao(ex: balao1.txt, balao2.txt), que esta na pasta balao do projeto


*O processo de cada balão ao ser instanciado já saberá de antemão a lista com todas as ERB existentes. Essa lista deverá estar em um arquivo texto, onde cada linha no arquivo deverá conter informações sobre uma ERB especı́fica no seguinte formato:

iDUnicoERB,latitude,longitude. 

Exemplo:
192.168.1.101:1234,-27.12243,48.2929 |
192.168.1.101:2222,-27.22000,48.2929 |
192.168.1.244:1234,-27.33000,48.2929 - Ok 

- esta no arquivo ERBs.txt, que esta na pasta balao do projeto

-------------------------------

ERB:
*O processo de cada ERB terrestre ao ser instanciando já saberá de antemão seu identificador único (iDUnicoERB) e suas coordenadas geográficas (latitude e longitude);

iDUnicoERB deve ser: IP:PORTA

Essas informações poderiam ser fornecidas, por exemplo, por meio de argumentos de linha de comando ou por meio de um arquivo texto; - Ok 

- esta no arquivo de configuraçao da ERB(ex: ERB1.txt, ERB2.txt), que esta na pasta erb do projeto

--------------------------------
usuario:
*Usuário ja sabe de antemao qual o balao mais proximo - fornecido por meio de argumento de linha de comando. - Ok 
- Ao executar o arquivo do usuário será pedido para digitar o Ip e porta do balão que se deseja se conectar
