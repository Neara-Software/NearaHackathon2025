#!/bin/bash

rm -r NearaSampleBot
cp BTreeBot/ NearaSampleBot/ -r
perl-rename 's/BTreeBot/NearaSampleBot/' NearaSampleBot/*
sed -i 's/BTree/NearaSample/g' NearaSampleBot/*.sh
zip NearaSampleBot.zip -r NearaSampleBot/
