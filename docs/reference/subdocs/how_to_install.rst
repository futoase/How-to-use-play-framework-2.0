=================================
How to install playframework 2.0
=================================

``MacOS X(Lion 10.7.3)での環境を前提に書いております``

インストール
=============
- homebrew
- scala
- sbt
- playframework 2.0

homebrew
---------
- homebrew をオフィシャルサイトに掲載されているrubyスクリプトを実行してインストールします

::
  
  /usr/bin/ruby -e "$(/usr/bin/curl -fksSL https://raw.github.com/mxcl/homebrew/master/Library/Contributions/install_homebrew.rb)"

scala
-------
::

  brew install scala

sbt
----
::

  brew install sbt

playframework 2.0
------------------
::
  
  curl -O http://download.playframework.org/releases/play-2.0.zip && unzip play-2.0.zip && mkdir ~/local && mv play-2.0 ~/local

- PATHに~/local/play-2.0を追加しましょう :)

気力
-----
- それとレッドブル
