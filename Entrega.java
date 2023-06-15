import java.lang.AssertionError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/*
 * Aquesta entrega consisteix en implementar tots els mètodes annotats amb el comentari "// TO DO".
 *
 * L'avaluació consistirà en:
 *
 * - Si el codi no compila, la nota del grup serà de 0.
 *
 * - Principalment, el correcte funcionament de cada mètode (provant amb diferents entrades). Teniu
 *   alguns exemples al mètode `main`.
 *
 * - Tendrem en compte la neteja i organització del codi. Un estandard que podeu seguir és la guia
 *   d'estil de Google per Java: https://google.github.io/styleguide/javaguide.html.  Algunes
 *   consideracions importants: indentació i espaiat consistent, bona nomenclatura de variables,
 *   declarar les variables el més aprop possible al primer ús (és a dir, evitau blocs de
 *   declaracions). També convé utilitzar el for-each (for (int x : ...)) enlloc del clàssic (for
 *   (int i = 0; ...)) sempre que no necessiteu l'índex del recorregut.
 *
 * Per com està plantejada aquesta entrega, no necessitau (ni podeu) utilitzar cap `import`
 * addicional, ni mètodes de classes que no estiguin ja importades. El que sí podeu fer és definir
 * tots els mètodes addicionals que volgueu (de manera ordenada i dins el tema que pertoqui).
 *
 * Podeu fer aquesta entrega en grups de com a màxim 3 persones, i necessitareu com a minim Java 8.
 * Per entregar, posau a continuació els vostres noms i entregau únicament aquest fitxer.
 * - Nom 1:Mukhammad Gandarov
 * - Nom 2:Bratlie Bastidas Rivera
 * - Nom 3:Daniel Trías Mateu
 *
 * L'entrega es farà a través d'una tasca a l'Aula Digital que obrirem abans de la data que se us
 * hagui comunicat i vos recomanam que treballeu amb un fork d'aquest repositori per seguir més
 * fàcilment les actualitzacions amb enunciats nous. Si no podeu visualitzar bé algun enunciat,
 * assegurau-vos de que el vostre editor de texte estigui configurat amb codificació UTF-8.
 */
class Entrega {
  /*
   * Aquí teniu els exercicis del Tema 1 (Lògica).
   *
   * Els mètodes reben de paràmetre l'univers (representat com un array) i els predicats adients
   * (per exemple, `Predicate<Integer> p`). Per avaluar aquest predicat, si `x` és un element de
   * l'univers, podeu fer-ho com `p.test(x)`, que té com resultat un booleà (true si `P(x)` és
   * cert). Els predicats de dues variables són de tipus `BiPredicate<Integer, Integer>` i
   * similarment s'avaluen com `p.test(x, y)`.
   *
   * En cada un d'aquests exercicis us demanam que donat l'univers i els predicats retorneu `true`
   * o `false` segons si la proposició donada és certa (suposau que l'univers és suficientment
   * petit com per poder provar tots els casos que faci falta).
   */
  static class Tema1 {
    /*
     * És cert que ∀x ∃!y. P(x) -> Q(x,y) ?
     */
    static boolean exercici1(int[] universe, Predicate<Integer> p, BiPredicate<Integer, Integer> q) {
      for (int x : universe) {
            boolean foundY = false;

            for (int y : universe) {
                if (p.test(x) && q.test(x, y)) {
                    foundY = true;
                    break;
                }
            }

            if (!foundY) {
                return false;
            }
        }

        return true;
    }
  

    /*
     * És cert que ∃!x ∀y. P(y) -> Q(x,y) ?
     */
    static boolean exercici2(int[] universe, Predicate<Integer> p, BiPredicate<Integer, Integer> q) {
      for (int y : universe) {  
            if (p.test(y)) {
                boolean existsX = false;
                for (int x : universe) {
                    if (q.test(x, y)) {
                        //Si hay más de una x, no se cumple
                        if (existsX) {
                            return false;
                        } else {
                            existsX = true;
                        }
                    }
                }
                //Si no hay ninguna x, no se cumple
                if (!existsX) {
                    return false;
                }
            }
        }
        return true;
    }

    /*
     * És cert que ∃x,y ∀z. P(x,z) ⊕ Q(y,z) ?
     */
    static boolean exercici3(int[] universe, BiPredicate<Integer, Integer> p, BiPredicate<Integer, Integer> q) {
       boolean found = false;
        for (int x : universe) {
          for (int y : universe) {
            found = true;
            for (int z : universe) {
              if ((!p.test(x, z) && !q.test(y, z)) || (p.test(x, z) && q.test(y, z))) {
                found = false;
                break;
              }
            }
            if (found) {
              break;
            }
          }
          if (found) {
            break;
          }
        }
        return found;
    }
    /*
     * És cert que (∀x. P(x)) -> (∀x. Q(x)) ?
     */
    static boolean exercici4(int[] universe, Predicate<Integer> p, Predicate<Integer> q) {
      boolean auxQ = true, auxP = true;
          // Como hay ∀x. Q(x) significa que si encontramos 1 caso donde no se cumpla,
          // (∀x. Q(x)) sera falso
          for (int x : universe) {
              if (!p.test(x)) {
                  auxP = false;
              }
              if (!q.test(x)) {
                  auxQ = false;
              }
          }
          //El unico caso donde donde una implicacion es falsa es cuando P = 1 y Q = 0 
          if (auxP && !auxQ) {
              return false;
          }
          return true; 
    }
  

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // ∀x ∃!y. P(x) -> Q(x,y) ?

      assertThat(
          exercici1(
              new int[] { 2, 3, 5, 6 },
              x -> x != 4,
              (x, y) -> x == y
          )
      );

      assertThat(
          !exercici1(
              new int[] { -2, -1, 0, 1, 2, 3 },
              x -> x != 0,
              (x, y) -> x * y == 1
          )
      );

      // Exercici 2
      // ∃!x ∀y. P(y) -> Q(x,y) ?

      assertThat(
          exercici2(
              new int[] { -1, 1, 2, 3, 4 },
              y -> y <= 0,
              (x, y) -> x == -y
          )
      );

      assertThat(
          !exercici2(
              new int[] { -2, -1, 1, 2, 3, 4 },
              y -> y < 0,
              (x, y) -> x * y == 1
          )
      );

      // Exercici 3
      // ∃x,y ∀z. P(x,z) ⊕ Q(y,z) ?

      assertThat(
          exercici3(
              new int[] { 2, 3, 4, 5, 6, 7, 8 },
              (x, z) -> z % x == 0,
              (y, z) -> z % y == 1
          )
      );

      assertThat(
          !exercici3(
              new int[] { 2, 3 },
              (x, z) -> z % x == 1,
              (y, z) -> z % y == 1
          )
      );

      // Exercici 4
      // (∀x. P(x)) -> (∀x. Q(x)) ?

      assertThat(
          exercici4(
              new int[] { 0, 1, 2, 3, 4, 5, 8, 9, 16 },
              x -> x % 2 == 0, // x és múltiple de 2
              x -> x % 4 == 0 // x és múltiple de 4
          )
      );

      assertThat(
          !exercici4(
              new int[] { 0, 2, 4, 6, 8, 16 },
              x -> x % 2 == 0, // x és múltiple de 2
              x -> x % 4 == 0 // x és múltiple de 4
          )
      );
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 2 (Conjunts).
   *
   * Per senzillesa tractarem els conjunts com arrays (sense elements repetits). Per tant, un
   * conjunt de conjunts d'enters tendrà tipus int[][].
   *
   * Les relacions també les representarem com arrays de dues dimensions, on la segona dimensió
   * només té dos elements. Per exemple
   *   int[][] rel = {{0,0}, {1,1}, {0,1}, {2,2}};
   * i també donarem el conjunt on està definida, per exemple
   *   int[] a = {0,1,2};
   *
   * Les funcions f : A -> B (on A i B son subconjunts dels enters) les representam donant el domini
   * int[] a, el codomini int[] b, i f un objecte de tipus Function<Integer, Integer> que podeu
   * avaluar com f.apply(x) (on x és d'a i el resultat f.apply(x) és de b).
   */
  static class Tema2 {
    /*
     * Comprovau si la relació `rel` definida sobre `a` és d'equivalència.
     *
     * Podeu soposar que `a` està ordenat de menor a major.
     */
    static boolean exercici1(int[] a, int[][] rel) {
        for (int i = 0; i < rel.length; i++) {
          int x = rel[i][0];
          int y = rel[i][1];

          // Comprobamos la reflexividad
          if (x != y) {
            return false;
          }

          // Comprobamos la simetría
          int[] reversePair = {y, x};
          if (!containsPair(rel, reversePair)) {
            return false;
          }

          // Comprobamos la transitividad
          for (int j = 0; j < rel.length; j++) {
            int z = rel[j][0];
            int w = rel[j][1];
            if (y == z && !containsPair(rel, new int[]{x, w})) {
              return false;
            }
          }
        }

        return true;
    }

    // Verifica si un par (x, y) se encuentra en la relación rel
    static boolean containsPair(int[][] rel, int[] pair) {
      for (int i = 0; i < rel.length; i++) {
        if (rel[i][0] == pair[0] && rel[i][1] == pair[1]) {
          return true;
        }
      }
      return false;
    }

    /*
     * Comprovau si la relació `rel` definida sobre `a` és d'equivalència. Si ho és, retornau el
     * cardinal del conjunt quocient de `a` sobre `rel`. Si no, retornau -1.
     *
     * Podeu soposar que `a` està ordenat de menor a major.
     */
    static int exercici2(int[] a, int[][] rel) {
        //Creamos un array para guardar los representantes
        int[] representative = new int[a.length];
        Arrays.fill(representative, -1);

        int cardinal = 0;
        // Iteramos por los elementos de 'a'
        for (int i = 0; i < a.length; i++) {
            int rep = findRepresentative(representative, i);
            if (rep == -1) {
                // Si no tiene representante, lo establecemos como su propio representante
                rep = i;
                representative[i] = i;
                cardinal++;
            }
            // Comparamos el elemento actual con los elementos restantes
            for (int j = i + 1; j < a.length; j++) {
                // Comprovamos si son equivalentes según la relación
                if (equivalent(a[i], a[j], rel)) {
                    int repJ = findRepresentative(representative, j);
                    // Si ya tiene un representante i es diferente del actual, 
                    //no es una relación de equivalencia
                    if (repJ != -1 && repJ != rep) {
                        return -1;
                    }
                    // Establecemos el representante del elemento j como el actual
                    //representante
                    if (repJ == -1) {
                        representative[j] = rep;
                    }
                }
            }
        }

        // Verificar si la relación es reflexiva
        for (int i = 0; i < a.length; i++) {
            if (!equivalent(a[i], a[i], rel)) {
                return -1;
            }
        }

        return cardinal;
    }

        static boolean esRelacioEquivalencia(int[] a, int[][] rel) {
            // Verificar si la relación es simétrica
            for (int i = 0; i < a.length; i++) {
                for (int j = i + 1; j < a.length; j++) {
                    if (equivalent(a[i], a[j], rel) != equivalent(a[j], a[i], rel)) {
                        return false;
                    }
                }
            }

            // Verificar si la relación es transitiva
            for (int i = 0; i < a.length; i++) {
                for (int j = i + 1; j < a.length; j++) {
                    for (int k = j + 1; k < a.length; k++) {
                        if (equivalent(a[i], a[j], rel) && equivalent(a[j], a[k], rel) && !equivalent(a[i], a[k], rel)) {
                            return false;
                        }
                    }
                }
            }

            return true;
        }

        static boolean equivalent(int x, int y, int[][] rel) {
            for (int[] pair : rel) {
                if (pair[0] == x && pair[1] == y) {
                    return true;
                }
            }
            return false;
        }
        // Encontrar el representante del elemento en el array de representantes
        static int findRepresentative(int[] representative, int index) {
            // Si no tiene representante
            if (representative[index] == -1) {
                return -1;
            }
            // Si el representante es el propio elemento
            if (representative[index] == index) {
                return index;
            }
            return findRepresentative(representative, representative[index]);
        }

    /*
     * Comprovau si la relació `rel` definida entre `a` i `b` és una funció.
     *
     * Podeu soposar que `a` i `b` estan ordenats de menor a major.
     */
    static boolean exercici3(int[] a, int[] b, int[][] rel) {
        //Verificamos si todo los elementos de 'a' estan en la primera parte de 'rel'
        for (int i = 0; i < a.length; i++) {
            boolean found = false;
            //Verificamos si todos los elementos de 'a' estan en la segunda parte de 'rel'
            for (int j = 0; j < rel.length; j++) {
                if (rel[j][0] == a[i]) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        //Verificamos si no hay duplicados en la relación
        for (int i = 0; i < rel.length; i++) {
            int contador = 0;
            for (int j = 0; j < rel.length; j++) {
                if (rel[i][0] == rel[j][0] && rel[i][1] == rel[j][1]) {
                    contador++;
                }
            }
            if (contador > 1) {
                return false;
            }
        }
        return true;
    }

    /*
     * Suposau que `f` és una funció amb domini `dom` i codomini `codom`.  Retornau:
     * - Si és exhaustiva, el màxim cardinal de l'antiimatge de cada element de `codom`.
     * - Si no, si és injectiva, el cardinal de l'imatge de `f` menys el cardinal de `codom`.
     * - En qualsevol altre cas, retornau 0.
     *
     * Podeu suposar que `dom` i `codom` estàn ordenats de menor a major.
     */
    static int exercici4(int[] dom, int[] codom, Function<Integer, Integer> f) {
      int[] im2 = new int[dom.length];
      Set<Integer> imagen2 = new HashSet<>();

      for (int i = 0; i < dom.length; i++) {//Generamos el conjunto de las imágenes
        im2[i] = f.apply(dom[i]);
        imagen2.add(im2[i]);
      }
      // Si es exhaustiva devolvemos el cardinal máximo
      if(Exhaustiva(dom,codom,f)){ 
        int cMax = 0, aux;
        for(int i = 0; i < im2.length; i++) {
          aux = 0;
          for(int j = 0; j < im2.length; j++) {
            if(im2[j] == im2[i]){
              aux++;
            }
          }
          if(aux > cMax) cMax = aux;
        }
        return cMax;
      }
      // Si es inyectiva devolmeos cardinal de la imagen - el cardinal de codom
      if(Inyectiva(dom,codom,f)){
        return imagen2.size() - codom.length;
      }

      return 0;
    }
    //MÉTODOS TEMA 2 EJERCICIO 4<

      
      
      static boolean Inyectiva(int[] dom, int[] codom, Function<Integer, Integer> f) {
          boolean inyectiva = true;
          int[] im1 = new int[dom.length];
          Set<Integer> im2 = new HashSet<>();
          for (int i = 0; i < dom.length; i++) {//Generamos el conjunto de las imágenes
              im1[i] = f.apply(dom[i]);
              im2.add(im1[i]);
          }
          for (int i : dom) {//Comprobamos que sea inyectiva
              for (int j : dom) {
                  if ((f.apply(i).equals(f.apply(j))) && (i != j)) {
                      inyectiva = false;
                  }
              }
          }
          return inyectiva;
      }

      static boolean Exhaustiva(int[] dom, int[] codom, Function<Integer, Integer> f) {
          boolean exhaustiva = true;
          int[] im1 = new int[dom.length];
          Set<Integer> im2 = new HashSet<>();
          for (int i = 0; i < dom.length; i++) {//Generamos el conjunto de las imágenes
              im1[i] = f.apply(dom[i]);
              im2.add(im1[i]);
          }
          if (codom.length > dom.length) {
              exhaustiva = false;
          }
          for (int a : codom) { //Comprobamos que sea exhaustiva
              if (!im2.contains(a)) {
                  exhaustiva = false;
              }
          }
          return exhaustiva;
      }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // `rel` és d'equivalencia?

      assertThat(
          exercici1(
              new int[] { 0, 1, 2, 3 },
              new int[][] { {0, 0}, {1, 1}, {2, 2}, {3, 3}, {1, 3}, {3, 1} }
          )
      );

      assertThat(
          !exercici1(
              new int[] { 0, 1, 2, 3 },
              new int[][] { {0, 0}, {1, 1}, {2, 2}, {3, 3}, {1, 2}, {1, 3}, {2, 1}, {3, 1} }
          )
      );

      // Exercici 2
      // si `rel` és d'equivalència, quants d'elements té el seu quocient?

      final int[] int09 = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

      assertThat(
          exercici2(
            int09,
            generateRel(int09, int09, (x, y) -> x % 3 == y % 3)
          )
          == 3
      );

      assertThat(
          exercici2(
              new int[] { 1, 2, 3 },
              new int[][] { {1, 1}, {2, 2} }
          )
          == -1
      );

      // Exercici 3
      // `rel` és una funció?

      final int[] int05 = { 0, 1, 2, 3, 4, 5 };

      assertThat(
          exercici3(
            int05,
            int09,
            generateRel(int05, int09, (x, y) -> x == y)
          )
      );

      assertThat(
          !exercici3(
            int05,
            int09,
            generateRel(int05, int09, (x, y) -> x == y/2)
          )
      );

      // Exercici 4
      // el major |f^-1(y)| de cada y de `codom` si f és exhaustiva
      // sino, |im f| - |codom| si és injectiva
      // sino, 0

      assertThat(
          exercici4(
            int09,
            int05,
            x -> x / 4
          )
          == 0
      );

      assertThat(
          exercici4(
            int05,
            int09,
            x -> x + 3
          )
          == int05.length - int09.length
      );

      assertThat(
          exercici4(
            int05,
            int05,
            x -> (x + 3) % 6
          )
          == 1
      );
    }

    /// Genera un array int[][] amb els elements {a, b} (a de as, b de bs) que satisfàn pred.test(a, b)
    static int[][] generateRel(int[] as, int[] bs, BiPredicate<Integer, Integer> pred) {
      ArrayList<int[]> rel = new ArrayList<>();

      for (int a : as) {
        for (int b : bs) {
          if (pred.test(a, b)) {
            rel.add(new int[] { a, b });
          }
        }
      }

      return rel.toArray(new int[][] {});
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 3 (Grafs).
   *
   * Donarem els grafs en forma de diccionari d'adjacència, és a dir, un graf serà un array
   * on cada element i-èssim serà un array ordenat que contendrà els índexos dels vèrtexos adjacents
   * al i-èssim vèrtex. Per exemple, el graf cicle C_3 vendria donat per
   *
   *  int[][] g = {{1,2}, {0,2}, {0,1}}  (no dirigit: v0 -> {v1, v2}, v1 -> {v0, v2}, v2 -> {v0,v1})
   *  int[][] g = {{1}, {2}, {0}}        (dirigit: v0 -> {v1}, v1 -> {v2}, v2 -> {v0})
   *
   * Podeu suposar que cap dels grafs té llaços.
   */
  static class Tema3 {
    /*
     * Retornau l'ordre menys la mida del graf (no dirigit).
     */
    static int exercici1(int[][] g) {
          int ordre;
          int mida = 0;
          // A les matrius d'adjecència, tant files com columnes representen nodes.
          ordre = g.length;

          // Per a saber la mida (nombre d'arestes) he utilitzat el teorema de les
          // mans encaixades. (Suma tots els graus = el doble del número de les aristes)
          for (int i = 0; i < g.length; i++) {
              // Recorrem la fila, sumant tots els graus de tots el nodes
              for (int j = 0; j < g[i].length; j++) {
                  mida++;
              }
          }
          // Dividim el numero de connexions entre nodes (la suma de tots els graus)
          // entre dos
          return ordre - (mida / 2);
      } 


    /*
     * Suposau que el graf (no dirigit) és connex. És bipartit?
     */
    static boolean exercici2(int[][] g) {
      // un grafo bipartito es un grafo conexo cuyos vértices se pueden separar en dos conjuntos 
        // disjuntos dependiendo de que grado tengan sus vertices, por tanto el d(V1) = Vertices de
        // d(Vn) y d(Vn) = Vertices de d(V1)
        int grado1 = g[0].length;             //grado del primer nodo
        int grado2 = g[g.length-1].length;     //grado del último nodo
        int vg1 = 0;            // número de vertices del primero de los grados que hay
        int vg2 = 0;            // número de vertices del segundo de los grados que hay
        // si el grado del primer nodo y el grado del segundo nodo son iguales
        // el grafo no es bipartito
        if (grado1 == grado2) {
            return false;
        }
        //Recorremos la para saber cuantos vertices tienen d(V1) y que cuantos d(Vn)
        for (int i = 0; i < g.length; i++) {
            int j = 0;
            if (g[i].length == grado1) {
                vg1++;
            } else {
                vg2++;
            }
        }
        // Si d(V1) = Vertices de d(Vn) y d(Vn) = Vertices de d(V1) es bipartito
        if ((grado1 == vg2) && (grado2 == vg1)) {
            return true;
        }
        // Sino devolvemos false
        return false;
    }

    /*
     * Suposau que el graf és un DAG. Retornau el nombre de descendents amb grau de sortida 0 del
     * vèrtex i-èssim.
     */
    static int exercici3(int[][] g, int i) {
        int contador = 0;
        boolean[] visitados = new boolean[g.length];
        visitados[i] = true;

        int[] queue = new int[g.length];
        int front = 0, rear = 0;
        queue[rear++] = i;

        while (front != rear) {
            int actual = queue[front++];
            for (int vecino : g[actual]) {
                if (!visitados[vecino]) {
                    visitados[vecino] = true;
                    queue[rear++] = vecino;
                    if (g[vecino].length == 0) {
                        contador++;
                    }
                }
            }
        }

        return contador;
    }

    /*
     * Donat un arbre arrelat (dirigit, suposau que l'arrel es el vèrtex 0), trobau-ne el diàmetre
     * del graf subjacent. Suposau que totes les arestes tenen pes 1.
     */
    static int exercici4(int[][] g) {

    int maximaDistancia = 0;

    for (int i = 0; i < g.length; i++) {
        int[] distancias = new int[g.length];
        Arrays.fill(distancias, -1);
        distancias[i] = 0;

        int[] stack = new int[g.length];
        int top = 0;
        stack[top++] = i;

        while (top > 0) {
            int actual = stack[--top];

            for (int vecino : g[actual]) {
                if (distancias[vecino] == -1) {
                    distancias[vecino] = distancias[actual] + 1;
                    stack[top++] = vecino;
                }
            }
        }

        int maxDistanciaActual = Arrays.stream(distancias).max().getAsInt();
        if (maxDistanciaActual > maximaDistancia) {
            maximaDistancia = maxDistanciaActual;
        }
    }

    return maximaDistancia;
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      final int[][] undirectedK6 = {
        { 1, 2, 3, 4, 5 },
        { 0, 2, 3, 4, 5 },
        { 0, 1, 3, 4, 5 },
        { 0, 1, 2, 4, 5 },
        { 0, 1, 2, 3, 5 },
        { 0, 1, 2, 3, 4 },
      };

      /*
         1
      4  0  2
         3
      */
      final int[][] undirectedW4 = {
        { 1, 2, 3, 4 },
        { 0, 2, 4 },
        { 0, 1, 3 },
        { 0, 2, 4 },
        { 0, 1, 3 },
      };

      // 0, 1, 2 | 3, 4
      final int[][] undirectedK23 = {
        { 3, 4 },
        { 3, 4 },
        { 3, 4 },
        { 0, 1, 2 },
        { 0, 1, 2 },
      };

      /*
             7
             0
           1   2
             3   8
             4
           5   6
      */
     final int[][] directedG1 = {
        { 1, 2 }, // 0
        { 3 },    // 1
        { 3, 8 }, // 2
        { 4 },    // 3
        { 5, 6 }, // 4
        {},       // 5
        {},       // 6
        { 0 },    // 7
        {},
      };


      /*
              0
         1    2     3
            4   5   6
           7 8
      */

      final int[][] directedRTree1 = {
        { 1, 2, 3 }, // 0 = r
        {},          // 1
        { 4, 5 },    // 2
        { 6 },       // 3
        { 7, 8 },    // 4
        {},          // 5
        {},          // 6
        {},          // 7
        {},          // 8
      };

      /*
            0
            1
         2     3
             4   5
                6  7
      */

      final int[][] directedRTree2 = {
        { 1 },
        { 2, 3 },
        {},
        { 4, 5 },
        {},
        { 6, 7 },
        {},
        {},
      };

      assertThat(exercici1(undirectedK6) == 6 - 5*6/2);
      assertThat(exercici1(undirectedW4) == 5 - 2*4);

      assertThat(exercici2(undirectedK23));
      assertThat(!exercici2(undirectedK6));

      assertThat(exercici3(directedG1, 0) == 3);
      assertThat(exercici3(directedRTree1, 2) == 3);

      assertThat(exercici4(directedRTree1) == 5);
      assertThat(exercici4(directedRTree2) == 4);
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 4 (Aritmètica).
   *
   * Per calcular residus podeu utilitzar l'operador %, però anau alerta amb els signes.
   * Podeu suposar que cada vegada que se menciona un mòdul, és major que 1.
   */
  static class Tema4 {
    /*
     * Donau la solució de l'equació
     *
     *   ax ≡ b (mod n),
     *
     * Els paràmetres `a` i `b` poden ser negatius (`b` pot ser zero), però podeu suposar que n > 1.
     *
     * Si la solució és x ≡ c (mod m), retornau `new int[] { c, m }`, amb 0 ⩽ c < m.
     * Si no en té, retornau null.
     */
    static int[] exercici1(int a, int b, int n) {
          int d = mcd(n, a);

          if ((b % d) != 0) {
              return null;
          }

          int[] sol = euclides(n, a);
          int c = sol[1];
          int k = n / d;

          if (k < 0) {
              k = -1;
          }

          while (c < 0) {
              c += k;
          }
          return new int[]{c, k};
      }
      // MÉTODOS TEMA 4 EJERCICIO 1:

      static int mcd(int a, int b) {
          if (b == 0) {
              return a;
          }
          return mcd(b, a % b);
      }
      static int[] euclides(int n, int a) {
          boolean negA = false;
          int r0 = n, r1 = a, t;
          int x0 = 1, y0 = 0, q;
          int x1 = 0, y1 = 1, auxX, auxY;

          if (a < 0) {
              negA = true;
              r1= -1;
          }

          while (r1 > 0) {
              t = r1;
              r1 = r0 % r1;
              if (r1 <= 0) {
                  break;
              }
              q = r0 / t;
              r0 = t;

              auxX = x1;
              auxY = y1;

              x1 = x0 - q * x1;
              y1 = y0 - q * y1;

              x0 = auxX;
              y0 = auxY;
          }

          if (negA) {
              y1 *= -1;
          }

          return new int[]{x1, y1};
      } 


    /*
     * Donau la solució (totes) del sistema d'equacions
     *
     *  { x ≡ b[0] (mod n[0])
     *  { x ≡ b[1] (mod n[1])
     *  { x ≡ b[2] (mod n[2])
     *  { ...
     *
     * Cada b[i] pot ser negatiu o zero, però podeu suposar que n[i] > 1. També podeu suposar
     * que els dos arrays tenen la mateixa longitud.
     *
     * Si la solució és de la forma x ≡ c (mod m), retornau `new int[] { c, m }`, amb 0 ⩽ c < m.
     * Si no en té, retornau null.
     */
    static int[] exercici2a(int[] b, int[] n) {
        int x = 0;
        int N = 1;
        // Cálculo del producto de todos los elementos del array n
        for (int ni : n) {
            N = N * ni;
        }
        for (int i = 0; i < b.length; i++) {
            int Ni = N / n[i];
            int Mi = -1;
            // Cálculo del inverso modular de Ni en el módulo n[i]
            for (int j = 1; j < n[i]; j++) {
                if ((Ni * j) % n[i] == 1) {
                    Mi = j;
                    break;
                }
            }
            // Si no se ha encontrado el inverso modular, la solución no existe
            if (Mi == -1) {
                return null;
            }
            x = x + (b[i] * Ni * Mi);
        }
        // Reducción de x al rango del módulo N
        x = x % N;
        // Si x es negativo, se le suma N para asegurar que esté en el rango 0 <= x < N
        if (x < 0) {
            x = x + N;
        }
        return new int[]{x, N};
    }

    /*
     * Donau la solució (totes) del sistema d'equacions
     *
     *  { a[0]·x ≡ b[0] (mod n[0])
     *  { a[1]·x ≡ b[1] (mod n[1])
     *  { a[2]·x ≡ b[2] (mod n[2])
     *  { ...
     *
     * Cada a[i] o b[i] pot ser negatiu (b[i] pot ser zero), però podeu suposar que n[i] > 1. També
     * podeu suposar que els tres arrays tenen la mateixa longitud.
     *
     * Si la solució és de la forma x ≡ c (mod m), retornau `new int[] { c, m }`, amb 0 ⩽ c < m.
     * Si no en té, retornau null.
     */
    static int[] exercici2b(int[] a, int[] b, int[] n) {
        //Variable para almacenar la solución del sistema
        int x = 0;
        //Variable per almacenar el módulo total del sistema
        int m = 1;
        
        for (int i = 0; i < a.length; i++) {
            //Ajustamos los coeficientes si son negativos
            while (a[i] < 0) {
                a[i] = a[i] + n[i];
                b[i] = b[i] + n[i];
            }
            //Calculamos los residuos de los coeficientes i actualitzamos los valores
            a[i] = a[i] % n[i];
            b[i] = b[i] % n[i];
            // Verificamos si el coeficiente 'a' o el módulo 'n' son cero
            if (a[i] == 0 || n[i] == 0) return null;
            // Calculamos el máximo común divisor entre 'a' i 'n'
            int d = gcd(a[i], n[i]);
            // Verificamos si no hay solución para la equación actual
            if (b[i] % d != 0) return null;
            // Dividimos los coeficientes i el módulo por el MCD
            a[i] = a[i] / d;
            b[i] = b[i] / d;
            n[i] = n[i] / d;
            // Verificar si el módulo es cero
            if (n[i] == 0) return null;
            // Calculamos la solución de la equación actual utilizando el Teorema Xinès 
            //dels residus
            int t = (b[i] * inversa(a[i], n[i])) % n[i];
            if ((t - x) % gcd(m, n[i]) != 0) return null;
            x = teoremaXines(x, m, t, n[i]);
            m = m * n[i];
        }
        // Ajustamos la solución si és negativa
        if (x < 0) x = x + m;
        return new int[]{x, m};
        }
        // Función para calcular el máximo común divisor utilizando el algoritmo 
        //de Euclides
        static int gcd(int a, int b) {
            return b == 0 ? a : gcd(b, a % b);
        }
        // Función para calcular el inverso multiplicativo utilizando el algoritme
        //extendido de Euclides
        static int inversa(int a, int m) {
            if (m == 1 || m == 0) return 0;
            int m0 = m;
            int y = 0;
            int x = 1;
            
            while (a > 1) {
                if (m == 0) return 0;
                int q = a / m;
                int t = m;
                m = a % m;
                a = t;
                t = y;
                y = x - q * y;
                x = t;
            }
            if (x < 0) x += m0;
            return x;
        }
        // Función para calcular la solución utilizando el Teorema Xinès dels residus
        static int teoremaXines(int x1, int m1, int x2, int m2) {
            if (m1 == 0 || m2 == 0) return 0;
            return ((x2 - x1) * inversa(m1, m2) % m2 * m1 + x1) % (m1 * m2);
        }

    /*
     * Suposau que n > 1. Donau-ne la seva descomposició en nombres primers, ordenada de menor a
     * major, on cada primer apareix tantes vegades com el seu ordre. Per exemple,
     *
     * exercici4a(300) --> new int[] { 2, 2, 3, 5, 5 }
     *
     * No fa falta que cerqueu algorismes avançats de factorització, podeu utilitzar la força bruta
     * (el que coneixeu com el mètode manual d'anar provant).
     */
    static ArrayList<Integer> exercici3a(int n) {
        //Guardamos los factores primos de n
        ArrayList<Integer> factors = new ArrayList<>();
        int divisor = 2;

        while (n > 1) {
            //Comprovamos si es divisible por el factor actual
            if (n % divisor == 0) {
                factors.add(divisor);
                //Actualitzamos el valor de n dividiendolo per el factor primo
                n /= divisor;
            } else {
                divisor++;
            }
        }
        
        System.out.print("Exercici3a Tema 4 - (" + n + ")-->");
        System.out.println(factors);
        return factors;
    }

    /*
     * Retornau el nombre d'elements invertibles a Z mòdul n³.
     *
     * Alerta: podeu suposar que el resultat hi cap a un int (32 bits a Java), però n³ no té perquè.
     * De fet, no doneu per suposat que pogueu tractar res més gran que el resultat.
     *
     * No podeu utilitzar `long` per solucionar aquest problema. Necessitareu l'exercici 3a.
     * No, tampoc podeu utilitzar `double`.
     */
    static int exercici3b(int n) {
        ArrayList<Integer> factoritzacioN = exercici3a(n);
        int phi = 1;
        //Recorremos la factoritzación de N
        while (!factoritzacioN.isEmpty()) {
            int factorPrim = factoritzacioN.get(0);
            int repeticioFactorPrim = 0;
            //Eliminamos el número guardado en la posición actual, contando
            //cuantas veces se repite en la factoritzación
            while (!factoritzacioN.isEmpty() && factoritzacioN.get(0) == factorPrim) {
                repeticioFactorPrim++;
                //Quitamos el factor contado del array, para poder pasar a analizar
                //el siguiente
                factoritzacioN.remove(0);
            }
            //Calculamos phi de manera que, siendo p un número primo, entonces ϕ(p) = p^n - p^(n-1)
            phi = phi * (int) (Math.pow(factorPrim, repeticioFactorPrim) - Math.pow(factorPrim, repeticioFactorPrim - 1));
        }
        //Calculamos phi de n^3, siendo la phi de un número elevado equivalente a la 
        //phi de sus factores
        phi = phi * n * n;
        System.out.println("Exercici3b Tema 4: " + phi);
        return phi;
        }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      assertThat(Arrays.equals(exercici1(17, 1, 30), new int[] { 23, 30 }));
      assertThat(Arrays.equals(exercici1(-2, -4, 6), new int[] { 2, 3 }));
      assertThat(exercici1(2, 3, 6) == null);

      assertThat(
        exercici2a(
          new int[] { 1, 0 },
          new int[] { 2, 4 }
        )
        == null
      );

      assertThat(
        Arrays.equals(
          exercici2a(
            new int[] { 3, -1, 2 },
            new int[] { 5,  8, 9 }
          ),
          new int[] { 263, 360 }
        )
      );

      assertThat(
        exercici2b(
          new int[] { 1, 1 },
          new int[] { 1, 0 },
          new int[] { 2, 4 }
        )
        == null
      );

      assertThat(
        Arrays.equals(
          exercici2b(
            new int[] { 2,  -1, 5 },
            new int[] { 6,   1, 1 },
            new int[] { 10,  8, 9 }
          ),
          new int[] { 263, 360 }
        )
      );

      assertThat(exercici3a(10).equals(List.of(2, 5)));
      assertThat(exercici3a(1291).equals(List.of(1291)));
      assertThat(exercici3a(1292).equals(List.of(2, 2, 17, 19 )));

      assertThat(exercici3b(10) == 400);

      // Aquí 1292³ ocupa més de 32 bits amb el signe, però es pot resoldre sense calcular n³.
      assertThat(exercici3b(1292) == 961_496_064);

      // Aquest exemple té el resultat fora de rang
      //assertThat(exercici3b(1291) == 2_150_018_490);
    }
  }

  /*
   * Aquest mètode `main` conté alguns exemples de paràmetres i dels resultats que haurien de donar
   * els exercicis. Podeu utilitzar-los de guia i també en podeu afegir d'altres (no els tendrem en
   * compte, però és molt recomanable).
   *
   * Podeu aprofitar el mètode `assertThat` per comprovar fàcilment que un valor sigui `true`.
   */
  public static void main(String[] args) {
    Tema1.tests();
    Tema2.tests();
    Tema3.tests();
    Tema4.tests();
  }

  /// Si b és cert, no fa res. Si b és fals, llança una excepció (AssertionError).
  static void assertThat(boolean b) {
    if (!b)
      throw new AssertionError();
  }
}

// vim: set textwidth=100 shiftwidth=2 expandtab :