PGDMP          :                y            blog    13.1    13.1     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16480    blog    DATABASE     Y   CREATE DATABASE blog WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.UTF-8';
    DROP DATABASE blog;
                blog    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                postgres    false            �           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                   postgres    false    3            �            1259    16500    account    TABLE     �   CREATE TABLE public.account (
    id bigint NOT NULL,
    email character varying(100) NOT NULL,
    name character varying(30) NOT NULL,
    avatar character varying(255),
    created timestamp(0) without time zone DEFAULT now() NOT NULL
);
    DROP TABLE public.account;
       public         heap    blog    false    3            �            1259    16517    account_seq    SEQUENCE     t   CREATE SEQUENCE public.account_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.account_seq;
       public          blog    false    3            �            1259    16489    article    TABLE     �  CREATE TABLE public.article (
    id bigint NOT NULL,
    title character varying(255) NOT NULL,
    url character varying(255) NOT NULL,
    logo character varying(255) NOT NULL,
    "desc" character varying(255) NOT NULL,
    content text NOT NULL,
    id_category integer NOT NULL,
    created timestamp(0) without time zone DEFAULT now() NOT NULL,
    views bigint DEFAULT 0 NOT NULL,
    comments integer DEFAULT 0 NOT NULL
);
    DROP TABLE public.article;
       public         heap    blog    false    3            �            1259    16481    category    TABLE     �   CREATE TABLE public.category (
    id integer NOT NULL,
    name character varying(20) NOT NULL,
    url character varying(20) NOT NULL,
    articles integer DEFAULT 0 NOT NULL
);
    DROP TABLE public.category;
       public         heap    blog    false    3            �            1259    16508    comment    TABLE     �   CREATE TABLE public.comment (
    id bigint NOT NULL,
    id_account bigint NOT NULL,
    id_arcticle bigint NOT NULL,
    content text NOT NULL,
    created timestamp(0) without time zone DEFAULT now() NOT NULL
);
    DROP TABLE public.comment;
       public         heap    blog    false    3            �            1259    16519    comment_seq    SEQUENCE     t   CREATE SEQUENCE public.comment_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.comment_seq;
       public          blog    false    3            �          0    16500    account 
   TABLE DATA           C   COPY public.account (id, email, name, avatar, created) FROM stdin;
    public          blog    false    202            �          0    16489    article 
   TABLE DATA           o   COPY public.article (id, title, url, logo, "desc", content, id_category, created, views, comments) FROM stdin;
    public          blog    false    201            �          0    16481    category 
   TABLE DATA           ;   COPY public.category (id, name, url, articles) FROM stdin;
    public          blog    false    200            �          0    16508    comment 
   TABLE DATA           P   COPY public.comment (id, id_account, id_arcticle, content, created) FROM stdin;
    public          blog    false    203            �           0    0    account_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.account_seq', 1, false);
          public          blog    false    204            �           0    0    comment_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.comment_seq', 1, false);
          public          blog    false    205            I           2606    16505    account account_pk 
   CONSTRAINT     P   ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_pk PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.account DROP CONSTRAINT account_pk;
       public            blog    false    202            K           2606    16507    account account_un 
   CONSTRAINT     N   ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_un UNIQUE (email);
 <   ALTER TABLE ONLY public.account DROP CONSTRAINT account_un;
       public            blog    false    202            G           2606    16499    article article_pk 
   CONSTRAINT     P   ALTER TABLE ONLY public.article
    ADD CONSTRAINT article_pk PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.article DROP CONSTRAINT article_pk;
       public            blog    false    201            B           2606    16486    category category_pk 
   CONSTRAINT     R   ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pk PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.category DROP CONSTRAINT category_pk;
       public            blog    false    200            D           2606    16488    category category_un 
   CONSTRAINT     N   ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_un UNIQUE (url);
 >   ALTER TABLE ONLY public.category DROP CONSTRAINT category_un;
       public            blog    false    200            O           2606    16516    comment comment_pk 
   CONSTRAINT     P   ALTER TABLE ONLY public.comment
    ADD CONSTRAINT comment_pk PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.comment DROP CONSTRAINT comment_pk;
       public            blog    false    203            E           1259    16537    article_id_category_idx    INDEX     R   CREATE INDEX article_id_category_idx ON public.article USING btree (id_category);
 +   DROP INDEX public.article_id_category_idx;
       public            blog    false    201            L           1259    16538    comment_id_account_idx    INDEX     P   CREATE INDEX comment_id_account_idx ON public.comment USING btree (id_account);
 *   DROP INDEX public.comment_id_account_idx;
       public            blog    false    203            M           1259    16536    comment_id_arcticle_idx    INDEX     R   CREATE INDEX comment_id_arcticle_idx ON public.comment USING btree (id_arcticle);
 +   DROP INDEX public.comment_id_arcticle_idx;
       public            blog    false    203            P           2606    16531    article article_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.article
    ADD CONSTRAINT article_fk FOREIGN KEY (id_category) REFERENCES public.category(id) ON UPDATE CASCADE ON DELETE RESTRICT;
 <   ALTER TABLE ONLY public.article DROP CONSTRAINT article_fk;
       public          blog    false    200    201    3138            Q           2606    16521    comment comment_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.comment
    ADD CONSTRAINT comment_fk FOREIGN KEY (id_account) REFERENCES public.account(id) ON UPDATE CASCADE ON DELETE RESTRICT;
 <   ALTER TABLE ONLY public.comment DROP CONSTRAINT comment_fk;
       public          blog    false    202    203    3145            R           2606    16526    comment comment_fk_1    FK CONSTRAINT     �   ALTER TABLE ONLY public.comment
    ADD CONSTRAINT comment_fk_1 FOREIGN KEY (id_arcticle) REFERENCES public.article(id) ON UPDATE CASCADE ON DELETE RESTRICT;
 >   ALTER TABLE ONLY public.comment DROP CONSTRAINT comment_fk_1;
       public          blog    false    201    3143    203            �      x������ � �      �      x������ � �      �      x������ � �      �      x������ � �     