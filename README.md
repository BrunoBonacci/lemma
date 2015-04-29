# lemma

A wrapper for Stanford CoreNLP lemmatizer.
It is designed to *"lemmatise"* text files in large number.

## About 'lemmatisation'

> Lemmatisation (or lemmatization) in linguistics is the process of
> grouping together the different inflected forms of a word so they can be
> analysed as a single item.
>  
> In computational linguistics, lemmatisation is the algorithmic process
> of determining the lemma for a given word. Since the process may involve
> complex tasks such as understanding context and determining the part of
> speech of a word in a sentence (requiring, for example, knowledge of the
> grammar of a language) it can be a hard task to implement a lemmatiser
> for a new language.
_(from Wikipedia http://en.wikipedia.org/wiki/Lemmatisation)_

For example it turns:

  * `cats` into `cat`
  * `men` into `man`
  * `walking` into `walk`


## Usage

```
SYNOPSIS
       
       lemmatizer -s INPUT -o OUTPUT
       
  -s, --source PATH  A file or directory containing text file to process
  -o, --output PATH  A file or directory which will contains the output files.
  -h, --help         This help
```

  * `-s, --source PATH` (**REQUIRED**) 
  
    This can be either a file or a directory and it represents the
    file(s) to process.  The source path or file must exists.
    
  * `-o, --output PATH` (**REQUIRED**)
  
    This can be either a file or a path and it represents the where the
    processed files must be written. The specified file or directory
    MUST NOT exists.  If the source path (`-s`) points to a file, this
    value will be considered to be the output filename. While if the
    source path is a directory, this value will be considered to be a
    folder. Files written in this directory will have the same directory
    structure of the files in the source directory.

## How to build

Just run:

```
lein do clean, bin
```
An executable will be generated as `./target/lemmatizer`

## License

Copyright © 2015 Bruno Bonacci

Distributed under the MIT License
