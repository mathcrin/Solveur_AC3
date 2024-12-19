%,Use,this,editor,as,a,MiniZinc,scratch,book
%On doit soortir une matrice d'entier qui correpond à qaund la tache est exécuter
par int:nb_jobs = 20;

par int:nb_machines = 5;

%Ici les entier représente des unité des temps
array [1..nb_machines,1..nb_jobs] of par int:processing_times =
[|54,83,15,71,77,36,53,38,27,87,76,91,14,29,12,77,32,87,68,94
|79,3,11,99,56,70,99,60,5,56,3,61,73,75,47,14,21,86,5,77
|16,89,49,15,89,45,60,23,57,64,7,1,63,41,63,47,26,75,77,40
|66,58,31,68,78,91,13,59,49,85,85,9,39,41,56,40,54,77,51,31
|58,56,20,85,53,35,53,41,69,13,86,72,8,49,47,87,58,18,68,28
|];

array [1..nb_machines,1..nb_jobs] of var 0..5000:debut_des_taches;

%Contrainte disjonctive 
constraint forall(machine in 1..nb_machines,tache in 1..nb_jobs-1)(
(debut_des_taches[machine,tache]+ processing_times[machine,tache]) <= debut_des_taches[machine,tache+1]
\/ (debut_des_taches[machine,tache+1]+ processing_times[machine,tache+1]) <= debut_des_taches[machine,tache]
);

%Dans le cas ou les machine sont indépendant des autres
%solve minimize sum(machine in 1..nb_machines)(debut_des_taches[machine,nb_jobs]);

%Dans le cas ou la mchine 2 doit attendre que la tache 1 de la machine 1 soit fini pour commencer sa tâche 1 
%contraine de Précédence
constraint forall(machine in 1..nb_machines-1,tache in 1..nb_jobs)((debut_des_taches[machine,tache]+ processing_times[machine,tache])<= debut_des_taches[machine+1,tache]);

%solve minimize debut_des_taches[nb_machines,nb_jobs];

solve minimize sum(i in 1..nb_jobs)(debut_des_taches[nb_machines,i]);