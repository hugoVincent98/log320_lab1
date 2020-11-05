# log320_Lab1
Algorithmes de compression LZW et Huffman



Il y a de plus de données disponibles et plusieurs de ces types de données occupent beaucoup d’espace (ou doivent
utiliser beaucoup de bande passante). La vidéo est un exemple commun de données qui utilise beaucoup de bande
passante. Les fichiers vidéo sont généralement compressés avant d’être transférés ou sauvegardés. Les algorithmes
de transmission de données sont donc de plus en plus importants. Il existe deux types compression : avec et sans
perte. Les algorithmes sans perte permettent de décompresser le fichier et de retrouver exactement le fichier original.
Dans les compressions avec perte, ce n’est pas le cas. Le fichier décompressé peut être légèrement différent. C’est
le cas pour la compréhension vidéo pour laquelle la perte de qualité produite par la décompression peut ne pas être
visible à l’œil. Dans le cas de ce laboratoire, nous allons nous concentrer sur deux algorithmes sans perte : LZW et
Huffman.
Les objectifs de ce laboratoire sont :
- Comprendre les algorithmes proposés et les implémenter
- Implémenter efficacement les algorithmes
- Proposer des optimisations
- Analyser asymptotiquement l’algorithme
- Être en mesure de justifier les choix de conception
