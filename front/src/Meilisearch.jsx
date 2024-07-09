import React from 'react';
import { instantMeiliSearch } from '@meilisearch/instant-meilisearch';
import { InfiniteHits, InstantSearch, SearchBox } from 'react-instantsearch';
import 'instantsearch.css/themes/algolia.css';

const { searchClient } = instantMeiliSearch(
    'http://localhost:7700'
  );

const MeiliApp = () => (
    <InstantSearch
      indexName="SUrl"
      searchClient={searchClient}
    >
      <SearchBox />
      <InfiniteHits hitComponent={Hit} />
    </InstantSearch>
  );

const Hit = ({hit}) => (
    <article key={hit.id}>
        {/* <img src={hit.image} alt={hit.name}/> */}
        <h1>Title : {hit.title ? hit.title : "No Title"}</h1>
        <p>Origin : {hit.origin}</p>
        <p>ShortUrl : {hit.shortUrl}</p>
        <p>Content : {hit.content}</p>
    </article>

);

export default MeiliApp;