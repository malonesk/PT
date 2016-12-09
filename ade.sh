#!/bin/sh
HTML_FILE1="$(mktemp)"
HTML_FILE2="$(mktemp)"
COOKIES_FILE="$(mktemp)"
wget --save-cookies $COOKIES_FILE --keep-session-cookies -O $HTML_FILE1 "https://sedna.univ-fcomte.fr/direct/myplanning.jsp?top=top.self&ticket=ST-1889735-D7YUByVSGMKmgOmet7Ub-cas.univ-fcomte.fr"
wget --load-cookies $COOKIES_FILE -O $HTML_FILE2 "http://ade52-inpg.grenet.fr/ade/custom/modules/plannings/imagemap.jsp?clearTree=false&width=1044&height=629"
wget --load-cookies $COOKIES_FILE -O edt.gif "http://ade52-inpg.grenet.fr$(cat "$HTML_FILE2" | grep img | sed 's/^.*src="\([^"]*\)".*$/\1/g')"
echo "http://ade52-inpg.grenet.fr$(cat "$HTML_FILE2" | grep img | sed 's/^.*src="\([^"]*\)".*$/\1/g')"
 
rm -f $HTML_FILE1
rm -f $HTML_FILE2
rm -f $COOKIES_FILE